	; CSX program translated into Java bytecodes (Jasmin format)
	.class	public  p04csx
	.super	java/lang/Object
	.method	 public static  main([Ljava/lang/String;)V
	invokestatic	p04csx/main()V
	return
	.limit	stack  2
	.end	method
	.method	public static  main()V
	ldc	123
	istore	0
	ldc	0
	istore	1
	ldc	"Testing program p04csx\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"i1 = "
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	0
	invokestatic	CSXLib/printInt(I)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"i2 = "
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	1
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	return
	.limit	stack  25
	.limit	locals  2
	.end	method
