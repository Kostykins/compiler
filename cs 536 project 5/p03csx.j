	; CSX program translated into Java bytecodes (Jasmin format)
	.class	public  p03csx
	.super	java/lang/Object
	.method	 public static  main([Ljava/lang/String;)V
	invokestatic	p03csx/main()V
	return
	.limit	stack  2
	.end	method
	.method	public static  main()V
	ldc	"Testing program p03csx\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	123
	istore	0
	iastore
	ldc	1
	istore	0
	bastore
	return
	.limit	stack  25
	.limit	locals  0
	.end	method
