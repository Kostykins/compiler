	; CSX program translated into Java bytecodes (Jasmin format)
	.class	public  p42csx
	.super	java/lang/Object
	.method	 public static  main([Ljava/lang/String;)V
	invokestatic	p42csx/main()V
	return
	.limit	stack  2
	.end	method
	.method	public static  sum(II)I
	iload	0
	iload	1
	iadd
	ireturn
	ldc	0
	ireturn
	.limit	stack  25
	.limit	locals  2
	.end	method
	.method	public static  plus1(I)I
	ldc	1
	iload	0
	invokestatic	p42csx/sum(II)I
	ireturn
	ldc	0
	ireturn
	.limit	stack  25
	.limit	locals  1
	.end	method
	.method	public static  main()V
	ldc	"Testing Program p42csx"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	"100+1 = "
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	ldc	100
	invokestatic	p42csx/plus1(I)I
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
