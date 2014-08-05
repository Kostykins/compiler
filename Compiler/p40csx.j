	; CSX program translated into Java bytecodes (Jasmin format)
	.class	public  p40csx
	.super	java/lang/Object
	.method	 public static  main([Ljava/lang/String;)V
	invokestatic	p40csx/main()V
	return
	.limit	stack  2
	.end	method
	.method	public static  echo(I)I
	iload	0
	ireturn
	ldc	0
	ireturn
	.limit	stack  25
	.limit	locals  1
	.end	method
	.method	public static  main()V
	ldc	"Testing Program p40csx"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"1+2+3+4 = "
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	1
	invokestatic	p40csx/echo(I)I
	ldc	2
	invokestatic	p40csx/echo(I)I
	ldc	3
	invokestatic	p40csx/echo(I)I
	ldc	4
	invokestatic	p40csx/echo(I)I
	iadd
	iadd
	iadd
	invokestatic	CSXLib/printInt(I)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"Test compeleted"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	return
	.limit	stack  25
	.limit	locals  0
	.end	method
