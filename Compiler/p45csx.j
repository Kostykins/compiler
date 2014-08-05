	; CSX program translated into Java bytecodes (Jasmin format)
	.class	public  p45csx
	.super	java/lang/Object
	.method	 public static  main([Ljava/lang/String;)V
	invokestatic	p45csx/main()V
	return
	.limit	stack  2
	.end	method
	.method	public static  main()V
	ldc	97
	istore	0
	iload	0
	istore	1
	iastore
	ldc	"int value of myChar = "
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	iload	1
	invokestatic	CSXLib/printInt(I)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	return
	.limit	stack  25
	.limit	locals  2
	.end	method
