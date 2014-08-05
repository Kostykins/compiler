import java.io.*;



/*
 *  This Visitor class generates JVM assembler code (using Jasmin's format)
 *  for CSX lite in the Printstream afile. You'll need to extend it to
 *  handle all of CSX. Note that for some AST nodes (like asgNode) code generation
 *  for CSX is more complex than that needed for CSX lite.
 *  All methods marked TODO will have to be completed by you (for full CSX)
 */

public class CodeGenerating extends Visitor {
	
	PrintStream afile;	// File to generate JVM code into 
	String aCLASS;
	int cgErrors =  0;       // Total number of code generation errors 

	int numberOfLocals =  0; // Total number of local CSX-lite vars

	int labelCnt = 0;	// counter used to generate unique labels
	static methodDeclNode currentMethod;
	
	public enum adrValues{
		Global,
		Local,
		Stack,
		Literal,
		None;
	}
	//addressingValues adrValues;
	CodeGenerating(PrintStream f){
		afile=f;
	}

	static void assertCondition(boolean assertion){
		if (! assertion)
			 throw new RuntimeException();
	}

	String error(ASTNode n) {
		return "Error (line " + n.linenum + "): ";
        }

	// generate a comment
	 void  genComment(String text){
       	gen("; "+text);
	}

	// generate an instruction w/ 0 operands
	 void  gen(String opcode){
        	afile.println("\t"+opcode);
	}

        // generate an instruction w/ 1 operand
	void  gen(String opcode, String operand){
        	afile.println("\t"+opcode+"\t"+operand);
	}

        // generate an instruction w/ 1 integer operand
	void  gen(String opcode, int operand){
        	afile.println("\t"+opcode+"\t"+operand);
	}


	//  generate an instruction w/ 2 operands
	void  gen(String opcode, String operand1, String operand2){
        	afile.println("\t"+opcode+"\t"+ operand1+"  "+ operand2);
	}

	//  generate an instruction w/ 2 operands (String and int)
	void  gen(String opcode, String operand1, int operand2){
        	afile.println("\t"+opcode+"\t"+ operand1+"  "+operand2);
	}
	void genCall(String methodDescriptor){
			gen("invokestatic", methodDescriptor);
	}
	//      Generate a new label of form labeln (e.g., label7 or label123)
	String   genLab(){
                return "label"+labelCnt++;
	}

	//      Place a label in generated code
	void    defineLab(String label){
			//gen(label);
        	afile.println(label+":");
	}
	
	void branch(String label){
		gen("goto",label);
	}
	void branchZ(String label){
		gen("ifeq",label);
	}
	void loadI(int val){
		gen("ldc",val);
	}
	void loadGlobalInt(String name){
		gen("getstatic", name);
	}
	void loadLocalInt(int index){
		gen("iload", index);
	}
	void binOp(String op){
		gen(op);
	}
	void storeGlobalInt(String name){
		gen("putstatic", name);
	}
	void storeLocalInt(int index){
		gen("istore", index);
	}
	
	static Boolean isRelationalOp(int op) {
		switch (op) {
			case sym.EQ:
			case sym.NOTEQ:
			case sym.LT:
			case sym.LEQ:	
			case sym.GT:
	 		case sym.GEQ:
				return true;
			default:
				return false;
		}
	}
	static String relationCode(int op) {
		switch (op) {
			case sym.EQ:
				return "eq";
			case sym.NOTEQ:
				return "ne";
			case sym.LT:
				return "lt";
			case sym.LEQ:	
				return "le";
			case sym.GT:
				return "gt";
			case sym.GEQ:
				return "ge";
			default:
				return "";
		}
	}
	void branchRelationalCompare(int tokenCode, String label){
		gen("if_ifcmp"+relationCode(tokenCode), label);
	}
	void genRelationalOp(int opcode){
		String trueLab = genLab();
		String skip = genLab();
		branchRelationalCompare(opcode, trueLab);
		loadI(0);
		branch(skip);
		defineLab(trueLab);
		loadI(1);
		defineLab(skip);
	}
	static String selectOpCode(int op) {
		switch (op) {
			case sym.PLUS:
				return("iadd");
			case sym.MINUS:
				return("isub");
			case sym.TIMES:
				return("imul");
			case sym.SLASH:
				return("idiv");
			case sym.CAND:
				return("iand");
			case sym.COR:
				return("ior");
			default:
				assertCondition(false);
				return "";
		}
	}
	String typeCode(typeNode type){
		//Return typeCode
		if(type instanceof intTypeNode)
			return "I";
		else if(type instanceof charTypeNode)
			return "C";
		else if (type instanceof boolTypeNode)
			return "Z";
		else 
			return "V";
		
	}
	String typeCode(ASTNode.Types type){
		switch(type){
			case Integer:
				return "I";
			case Character:
				return "C";
			case Boolean:
				return "Z";
			case Void:
				return "V";
			default: return "V";
		}
	}
	String buildTypeCode(argDeclNode n){
		if(n instanceof valArgDeclNode){
			return typeCode(((valArgDeclNode) n).argType);
		} else{
			return typeCode(((arrayArgDeclNode) n).elementType);
		}
	}
	String buildTypeCode(argDeclsNode n){
		if(n.moreDecls.isNull()){
			return buildTypeCode(n.thisDecl);
		} else{
			return buildTypeCode(n.thisDecl) + buildTypeCode((argDeclsNode) n.moreDecls);
		}
	}
	String buildTypeCode(exprNode n){
		if(n.kind == ASTNode.Kinds.Array){
			return arrayTypeCode(n.type);
		}
		else{
			return typeCode(n.type);
		}
	}
	String buildTypeCode(argsNode n){
		if(n.moreArgs.isNull()){
			return buildTypeCode(n.argVal);
		} else{
			return buildTypeCode(n.argVal) + buildTypeCode((argsNode)n.moreArgs);
		}
	}
	String buildTypeCode(String methodName, argsNodeOption args, String returnCode){
		String newTypeCode = methodName;
		if(args.isNull()){
			newTypeCode = newTypeCode + "()";
		} else{
			newTypeCode = newTypeCode + "(" + buildTypeCode((argsNode)args) + ")";
		}
		return newTypeCode + returnCode;
	}
	//   startCodeGen translates the AST rooted by node n
  	//      into JVM code which is written in afile.
	//   If no errors occur during code generation,
	//    TRUE is returned, and afile should contain a
    //    complete and correct JVM program. 
	//   Otherwise, FALSE is returned and afile need not
	//    contain a valid program.
	
	boolean startCodeGen(csxLiteNode n) {// For CSX Lite
	    this.visit(n);
	    return (cgErrors == 0);
	}
	
	boolean startCodeGen(classNode n) {// For CSX
	    this.visit(n);
	    return (cgErrors == 0);
		}
	boolean isNumericLit(exprNodeOption initValue){
		return ((initValue instanceof intLitNode) ||
				(initValue instanceof charLitNode) ||
				(initValue instanceof trueNode) ||
				(initValue instanceof falseNode) 
				);
	}
	int getLitValue(exprNode e){
		if(e instanceof intLitNode){
			return ((intLitNode) e).intval;
		}
		else if(e instanceof charLitNode){
			return ((charLitNode) e).charval;
		}
		else if (e instanceof trueNode){
			return 1;
		}
		else if (e instanceof trueNode){
			return 0;
		}
		return 0;
	}
	void declGlobalInt(String name, exprNodeOption initValue){
		int numValue = 0;
		if(isNumericLit(initValue)){
			numValue = getLitValue((exprNode)initValue);
			gen(".field", "public static "+name ,numValue);
		} else{
			gen(".field", "public static "+ name,0);
		}
	}
	String arrayTypeCode(typeNode type){
		if(type instanceof intTypeNode){
			return "[I";
		} 
		else if(type instanceof charTypeNode){
			return "[C";
		}
		else if(type instanceof boolTypeNode){
			return "[Z";
		}
		return "[?";
	}
	static String arrayTypeCode(ASTNode.Types type){
		switch(type){
			case Integer: return "[I";
			case Character: return "[C";
			case Boolean: return "[Z";
			default: return "[?";
		}
	}
	
	void declGlobalArray(String name, typeNode type){
		gen(".field", "public static"+ name, arrayTypeCode(type));
	}
	void allocateArray(typeNode type){
		if(type instanceof intTypeNode){
			gen("newarray", "int");
		}
		else if(type instanceof charTypeNode){
			gen("newarray", "char");
		}
		else if(type instanceof boolTypeNode){
			gen("newarray", "boolean");
		}
	}
	void loadGlobalReference(String name, String typeCode){
		gen("getstatic", name, typeCode);
	}
	void storeGlobalReference(String name, String typeCode){
		gen("putstatic", name, typeCode);
	}
	void loadLocalReference(int index){
		gen("aload", index);
	}
	void storeLocalReference(int index){
		gen("astore", index);
	}
	void declField(varDeclNode n){
		String varLabel = n.varName.idname + "$";
		declGlobalInt(varLabel,n.initValue);
		n.varName.idinfo.label = varLabel;
		n.varName.idinfo.adr = adrValues.Global;
	}
	void declField(constDeclNode n){
		String varLabel = n.constName.idname + "$";
		declGlobalInt(varLabel,n.constValue);
		n.constName.idinfo.label = varLabel;
		n.constName.idinfo.adr = adrValues.Global;
	}
	void declField(arrayDeclNode n){
		String varLabel = n.arrayName.idname + "$";
		declGlobalArray(varLabel,n.elementType);
		n.arrayName.idinfo.label = varLabel;
		n.arrayName.idinfo.adr = adrValues.Global;
	}
	void computeAdr(nameNode name){
		if(name.subscriptVal.isNull()){
			if(name.varName.idinfo.kind == ASTNode.Kinds.Var ||
			   name.varName.idinfo.kind == ASTNode.Kinds.ScalarParm){
				//id is a scalar variable
				if(name.varName.idinfo.adr == adrValues.Global){
					name.adr = adrValues.Global;
					name.label = name.varName.idinfo.label;
					//loadGlobalInt(name.label);
				} else {
					name.adr = adrValues.Local;
					name.varIndex = name.varName.idinfo.varIndex;
					//loadLocalInt(name.varIndex);
				}
			}else {
				//varName is array var or array param
				if(name.varName.idinfo.adr == adrValues.Global){
					name.label = name.varName.idinfo.label;
					loadGlobalReference(name.label, arrayTypeCode(name.varName.idinfo.type));
				} else{
					name.varIndex = name.varName.idinfo.varIndex;
					loadLocalReference(name.varIndex);
				}
			}
			//name.adr = adrValues.Stack;
		} else {/*subscripted variables*/
			//Push array ref first
			if(name.varName.idinfo.adr == adrValues.Global){
				name.label = name.varName.idinfo.label;
				loadGlobalReference(name.label, arrayTypeCode(name.varName.idinfo.type));
			} else{
				name.varIndex = name.varName.idinfo.varIndex;
				loadLocalReference(name.varIndex);
			}//next compute subscript expression
			this.visit(name.subscriptVal);
		}
	}
	
	void storeID(identNode id){
		if(id.idinfo.kind == ASTNode.Kinds.Var || id.idinfo.kind == ASTNode.Kinds.Value){
			if(id.idinfo.adr == adrValues.Global){
				storeGlobalInt(id.idinfo.label);
			} else {
				storeLocalInt(id.idinfo.varIndex);
			}
		}
	}
	void storeName(nameNode name){
		if(name.subscriptVal.isNull()){
			if(name.varName.idinfo.kind == ASTNode.Kinds.Var ||
			   name.varName.idinfo.kind == ASTNode.Kinds.ScalarParm){
				if(name.adr == adrValues.Global){
					storeGlobalInt(name.label);
				} else {
					storeLocalInt(name.varIndex);
				}
			}else{//mus be an array
				//check length of source and target arrays
				switch(name.type){
					case Integer:
						break;
					case Boolean:
						genCall("CSXLib/checkBoolArrayLength([Z[Z)[Z");
						break;
					case Character:
						genCall("CSXLib/checkCharArrayLength([C[C)[C");
						break;
					default: break;	
				}//Now store source array in target var
				if(name.varName.idinfo.adr == adrValues.Global){
					name.label = name.varName.idinfo.label;
					storeGlobalReference(name.label, arrayTypeCode(name.varName.idinfo.type));
				}else {
					name.varIndex = name.varName.idinfo.varIndex;
					storeLocalReference(name.varIndex);
				}
			}
		}//this is a subscripted var
		// a ref to the target array, the subscript expr, and the source expr 
		// have already been pushed so store the source val into the array
		switch(name.type){
			case Integer:
				gen("iastore");
				break;
			case Boolean:
				gen("bastore");
				break;
			case Character:
				gen("castore");
				break;
			default: break;
		}
	}
 	void visit(csxLiteNode n) {
 		
	}

 	void visit(fieldDeclsNode n){
		this.visit(n.thisField);
		this.visit(n.moreFields);
	}
	
	void visit(nullFieldDeclsNode n){}
	
	void visit(stmtsNode n){
		 // System.out.println ("In stmtsNode\n");
		  this.visit(n.thisStmt);
		  this.visit(n.moreStmts);

	}
	
	void visit(nullStmtsNode n){}

	void visit(varDeclNode n){
		if(currentMethod == null){
			if(n.varName.idinfo.adr == adrValues.None){
				declField(n);
			} else{
				if(!n.initValue.isNull()){
					if(!isNumericLit(n.initValue)){
						this.visit(n.initValue);
						storeID(n.varName);
					}
				}
			}
		}else{
			 n.varName.idinfo.varIndex = currentMethod.name.idinfo.numberOfLocals;
			 n.varName.idinfo.adr = adrValues.Local;
			 currentMethod.name.idinfo.numberOfLocals++;  
		     //Do initialization (if necessary)
			 if(!n.initValue.isNull()){
				 this.visit(n.initValue);
				 storeID(n.varName);
			 }
		}
	}
	
	void visit(nullTypeNode n) {}

	void visit(intTypeNode n) {
		// No code generation needed
	}

	void visit(boolTypeNode n) {
		// No code generation needed
	}

	void visit(charTypeNode n) {
		// No code generation needed
	}

	void visit(voidTypeNode n) {
		// No code generation needed
	}
	
	void visit(asgNode n) {
		//computer adr associated with LHS
		computeAdr(n.target);
	 // Translate RHS (an expression)
    	this.visit(n.source);
    //check to see if source needs to be cloned or converted
    	if((n.source.kind == ASTNode.Kinds.Array) ||
    	   (n.source.kind == ASTNode.Kinds.ArrayParm))
    		switch(n.source.type){
    			case Integer:
    				genCall("CSXLib/cloneIntArray([I)[I");
    				break;
    			case Boolean:
    				genCall("CSXLib/cloneBoolArray([Z)[Z");
    				break;
    			case Character:
    				genCall("CSXLib/cloneCharArray([C)[C");
    				break;
    			default: genCall("CSXLib/cloneIntArray([I)[I"); break;
    		}
    	else if(n.source.kind == ASTNode.Kinds.String)
    		genCall("CSXLib/convertString(Ljava/lang/String;)[C");
    		
    // Value to be stored is now on the stack
    // Save it into LHS
    	storeName(n.target);
	}
	
	void visit(ifThenNode n) { //No else statement in CSX lite
	 	String    endLab;  // label that will mark end of if stmt
	 	String 	  elseLab; // label that will mark start of else part
	 	
        // translate boolean condition, pushing it onto the stack
        	this.visit(n.condition);

        	elseLab = genLab();
        	
        // generate conditional branch around then stmt
        	branchZ(elseLab);

        // translate then part
        	this.visit(n.thenPart);
        	endLab = genLab();
        	branch(endLab);
        	defineLab(elseLab);
        	this.visit(n.elsePart);
        // generate label marking end of if stmt
        	defineLab(endLab);
	}

	void visit(printNode n) {
		// compute value to be printed onto the stack
    	this.visit(n.outputValue);
    	if(n.outputValue.kind == ASTNode.Kinds.Array ||
    	   n.outputValue.kind == ASTNode.Kinds.ArrayParm){
    		genCall("CSXLib/printCharArray([C)V");
    	}
    	else if(n.outputValue.kind == ASTNode.Kinds.String){
    		genCall("CSXLib/printString(Ljava/lang/String;)V");
    	}
    	else switch(n.outputValue.type){
    		case Integer:
    			genCall("CSXLib/printInt(I)V");
    			break;
    		case Boolean:
    			genCall("CSXLib/printBool(Z)V");
    			break;
    		case Character:
    			genCall("CSXLib/printChar(C)V");
    			break;
    		default:
    			break;
    	}
    	this.visit(n.morePrints);
	}

	void visit(nullPrintNode n) {}
		
	void visit(blockNode n) {
		this.visit(n.decls);
		this.visit(n.stmts);
	}
	
	
	void visit(binaryOpNode n) {
		 // First translate the left and right operands
    	this.visit(n.leftOperand);
    	this.visit(n.rightOperand);
    // Now the values of the operands are on the stack
    // Is this a relational operator?
    	if(relationCode(n.operatorCode) == ""){
    		gen(selectOpCode(n.operatorCode));
    	} else{
    		genRelationalOp(n.operatorCode);
    	}
    	n.adr = adrValues.Stack;
	}
	
	
	void visit(identNode n) {
	// In CSX-lite, we don't code generate identNode directly.
       //  Instead, we do translation in parent nodes where the
       //   context of identNode is known
       // Hence no code generation actions are defined here 
	   // (though you may want/need to define some in full CSX)

	}
	
	void visit(intLitNode n) {
		loadI(n.intval);
		n.adr = CodeGenerating.adrValues.Literal;
	}
	
	void visit(nameNode n) {
		if(n.subscriptVal.isNull()){
			if(n.varName.idinfo.kind == ASTNode.Kinds.Var ||
			   n.varName.idinfo.kind == ASTNode.Kinds.Value ||
			   n.varName.idinfo.kind == ASTNode.Kinds.ScalarParm){
				//id is a scalar variable
				if(n.varName.idinfo.adr == adrValues.Global){
					//n.adr = adrValues.Global;
					n.label = n.varName.idinfo.label;
					loadGlobalInt(n.label);
				} else {
					//n.adr = adrValues.Local;
					n.varIndex = n.varName.idinfo.varIndex;
					loadLocalInt(n.varIndex);
				}
			}else {
				//varName is array var or array param
				if(n.varName.idinfo.adr == adrValues.Global){
					n.label = n.varName.idinfo.label;
					loadGlobalReference(n.label, arrayTypeCode(n.varName.idinfo.type));
				} else{
					n.varIndex = n.varName.idinfo.varIndex;
					loadLocalReference(n.varIndex);
				}
			}
			//n.adr = adrValues.Stack;
		} else {/*subscripted variables*/
			//Push array ref first
			if(n.varName.idinfo.adr == adrValues.Global){
				n.label = n.varName.idinfo.label;
				loadGlobalReference(n.label, arrayTypeCode(n.varName.idinfo.type));
			} else{
				n.varIndex = n.varName.idinfo.varIndex;
				loadLocalReference(n.varIndex);
			}//next compute subscript expression
			this.visit(n.subscriptVal);
			//load dat element onto dat stack
			switch(n.type){
				case Integer:
					gen("iaload");
					break;
				case Boolean:
					gen("baload");
					break;
				case Character:
					gen("caload");
					break;
			    default:
					break;
			}
		}
	}

	
	void visit(classNode n) {
		currentMethod = null; //we are not in a method body (yet)
		String CLASS = n.className.idname;
		aCLASS = CLASS;
		genComment("CSX program translated into Java bytecodes (Jasmin format)");
		gen(".class","public", n.className.idname);
    	gen(".super","java/lang/Object");
    	//gen field decls for the class
		this.visit(n.members.fields);
		gen(".method"," public static","main([Ljava/lang/String;)V");
		//gen non-trivail field decls
		this.visit(n.members.fields);
		gen("invokestatic",  n.className.idname+"/main()V");
    	gen("return");
    	gen(".limit","stack",2);
    	gen(".end","method");
    	this.visit(n.members.methods);

	}

	void visit(memberDeclsNode n) {
		this.visit(n.fields);
		this.visit(n.methods);
	}

	
	void visit(valArgDeclNode n) {
		n.argName.idinfo.adr = adrValues.Local;
		n.argName.idinfo.varIndex = currentMethod.name.idinfo.numberOfLocals++;

	}

	void visit(arrayArgDeclNode n) {
		n.argName.idinfo.adr = adrValues.Local;
		n.argName.idinfo.varIndex = currentMethod.name.idinfo.numberOfLocals++;

	}

	void visit(argDeclsNode n) {
		this.visit(n.thisDecl);
		this.visit(n.moreDecls);

	}


	void visit(nullArgDeclsNode n) {}


	void visit(methodDeclsNode n) {
		this.visit(n.thisDecl);
		if(!(n.moreDecls == null))
			this.visit(n.moreDecls);
	

	}

	void visit(nullMethodDeclsNode n) {}

	void visit(methodDeclNode n) {
		currentMethod = n; //wooo method get
		n.name.idinfo.numberOfLocals = 0;
		String newTypeCode = n.name.idname;
		if(n.args.isNull()){
			newTypeCode = newTypeCode + "()";
		} else {
			newTypeCode = newTypeCode + "(" + buildTypeCode((argDeclsNode)n.args) + ")";
		}
		newTypeCode = newTypeCode + typeCode(n.returnType);
		n.name.idinfo.methodReturnCode = typeCode(n.returnType);
		gen(".method", "public static", newTypeCode);
		this.visit(n.args);
		this.visit(n.decls);
		this.visit(n.stmts);
		if(n.returnType instanceof voidTypeNode){
			gen("return");
		}else{
			loadI(0);
			gen("ireturn");
		}
		//gen end of method data
		gen(".limit","stack",25);
		gen(".limit", "locals", n.name.idinfo.numberOfLocals);
		gen(".end", "method");

	}

	void visit(trueNode n) {
		loadI(1);
		n.adr = CodeGenerating.adrValues.Literal;
		n.intVal = 1;
		

	}

	void visit(falseNode n) {
		loadI(0);
		n.adr = CodeGenerating.adrValues.Literal;
		n.intVal = 0;

	}

	void visit(constDeclNode n) {
		if(currentMethod == null){
			if(n.constName.idinfo.adr == adrValues.None){
				declField(n);
			} else{
				if(!n.constValue.isNull()){
					if(!isNumericLit(n.constValue)){
						this.visit(n.constValue);
						storeID(n.constName);
					}
				}
			}
		}else{
			n.constName.idinfo.varIndex = currentMethod.name.idinfo.numberOfLocals;
		
			n.constName.idinfo.adr = adrValues.Local;
			currentMethod.name.idinfo.numberOfLocals++;  
			//Do initialization (if necessary)
		 	if(!n.constValue.isNull()){
		 		this.visit(n.constValue);
		 		storeID(n.constName);
		 	}
		}
	}

	void visit(arrayDeclNode n) {
		if(currentMethod == null){
			if(n.arrayName.idinfo.adr == adrValues.None){
				declField(n);
				return;
			}
		}else{//local array decl (do later)
			n.arrayName.idinfo.varIndex = currentMethod.name.idinfo.numberOfLocals;
			n.arrayName.idinfo.adr = adrValues.Local;
			currentMethod.name.idinfo.numberOfLocals++;
		}
		loadI(n.arraySize.intval);
		allocateArray(n.elementType);
		if(n.arrayName.idinfo.adr == adrValues.Global){
			storeGlobalReference(n.arrayName.idinfo.label,arrayTypeCode(n.elementType));
		} else{
			storeLocalReference(n.arrayName.idinfo.varIndex);
		}
	}


	void visit(readNode n) {
		computeAdr(n.targetVar);
		if(n.targetVar.varName.idinfo.type == ASTNode.Types.Integer){
			genCall("CSXLib/readInt()I");
		}else{
			genCall("CSXLib/readChar()C");
		}
		storeName(n.targetVar);
		this.visit(n.moreReads);
	}
	


	void visit(nullReadNode n) {}


	void visit(charLitNode n) {
		loadI(n.charval);
		n.adr = CodeGenerating.adrValues.Literal;
		n.intVal = n.charval;
	}

	void visit(strLitNode n) {
		gen("ldc", n.strval);

	}

	void visit(argsNode n) {
		this.visit(n.argVal);
		this.visit(n.moreArgs);

	}


	void visit(nullArgsNode n) {}

	
	void visit(unaryOpNode n) {
		// TODO Auto-generated method stub

	}


	void visit(nullStmtNode n) {}


	void visit(nullExprNode n) {}

	
	void visit(whileNode n) {
		String top = genLab();
		String bottom = genLab();
		if(!n.label.isNull()){
			((identNode)n.label).idinfo.topLabel = top;
			((identNode)n.label).idinfo.bottomLabel = bottom;
		}
		defineLab(top);
		this.visit(n.condition);
		branchZ(bottom);
		this.visit(n.loopBody);
		branch(top);
		defineLab(bottom);
	}

	void visit(callNode n) {
		this.visit(n.args);
		String typeCode = buildTypeCode(n.methodName.idname, n.args, n.methodName.idinfo.methodReturnCode);
		genCall(aCLASS + "/" +typeCode);
	}


	void visit(fctCallNode n) {
		this.visit(n.methodArgs);
		String typeCode = buildTypeCode(n.methodName.idname, n.methodArgs, n.methodName.idinfo.methodReturnCode);
		genCall(aCLASS + "/" +typeCode);

	}


	void visit(returnNode n) {
		if(n.returnVal.isNull()){
			gen("return");
		}
		else{
			this.visit(n.returnVal);
			gen("ireturn");
		}

	}

	void visit(breakNode n) {
		branch(n.label.idinfo.bottomLabel);

	}

	void visit(continueNode n) {
		branch(n.label.idinfo.topLabel);

	}


	void visit(castNode n) {
		//First Translate the operand
		this.visit(n.operand);
		// is it char or int? result type bool?
		if(((n.operand.type == ASTNode.Types.Integer) || (n.operand.type == ASTNode.Types.Character)) &&
			(n.resultType instanceof boolTypeNode)){
				loadI(0);
				genRelationalOp(sym.NOTEQ);
		} else if ((n.operand.type == ASTNode.Types.Integer) && 
				    (n.resultType instanceof charTypeNode)){
				loadI(127);
				gen("iand");
		}

	}
	
	 void visit(incrementNode n){
		if(n.target.subscriptVal.isNull()){
			//simple (unsubscripted) identifier
			this.visit(n.target);
			loadI(1);
			gen("iadd");
			computeAdr(n.target);
			storeName(n.target);
		} else{ //subscripted array element
			computeAdr(n.target);
			gen("dup2"); //duplicate array index and ref
			//Now load the array element onto the stack
			switch(n.target.type){
				case Integer:
					gen("iaload");
					break;
				case Boolean:
					gen("baload");
					break;
				case Character:
					gen("caload");
					break;
				default: break;
			}
			loadI(1);
			gen("iadd");
			storeName(n.target);
		}
	}
	 void visit(decrementNode n){
		 if(n.target.subscriptVal.isNull()){
				//simple (unsubscripted) identifier
				this.visit(n.target);
				loadI(1);
				gen("isub");
				computeAdr(n.target);
				storeName(n.target);
			} else{ //subscripted array element
				computeAdr(n.target);
				gen("dup2"); //duplicate array index and ref
				//Now load the array element onto the stack
				switch(n.target.type){
					case Integer:
						gen("iaload");
						break;
					case Boolean:
						gen("baload");
						break;
					case Character:
						gen("caload");
						break;
					default: break;
				}
				loadI(1);
				gen("isub");
				storeName(n.target);
			}
	}

}
