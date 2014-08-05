	; CSX program translated into Java bytecodes (Jasmin format)
	.class	public  p27csx
	.super	java/lang/Object
	.method	 public static  main([Ljava/lang/String;)V
	invokestatic	p27csx/main()V
	return
	.limit	stack  2
	.end	method
	.method	public static  proc1()I
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"Procedure 1 entered"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"Procedure 1 exited"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	123
	ireturn
	ldc	0
	ireturn
	.limit	stack  25
	.limit	locals  0
	.end	method
	.method	public static  proc2()Z
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"Procedure 2 entered"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"Procedure 2 exited"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	1
	ireturn
	ldc	0
	ireturn
	.limit	stack  25
	.limit	locals  0
	.end	method
	.method	public static  main()V
	ldc	"Testing Program p27csx"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	invokestatic	p27csx/proc1()I
	invokestatic	CSXLib/printInt(I)V
	invokestatic	p27csx/proc2()Z
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"Test compeleted"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	return
	.limit	stack  25
	.limit	locals  2
	.end	method
