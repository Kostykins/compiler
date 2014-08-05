	; CSX program translated into Java bytecodes (Jasmin format)
	.class	public  p24csx
	.super	java/lang/Object
	.method	 public static  main([Ljava/lang/String;)V
	invokestatic	p24csx/main()V
	return
	.limit	stack  2
	.end	method
	.method	public static  main()V
	ldc	10
	newarray	int
	astore	0
	ldc	2
	newarray	boolean
	astore	1
	ldc	"Testing program p24csx\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	aload	0
	ldc	0
	ldc	1
	iastore
	aload	0
	ldc	1
	ldc	10
	iastore
	aload	0
	ldc	2
	ldc	100
	iastore
	aload	0
	ldc	3
	ldc	1000
	iastore
	aload	0
	ldc	4
	ldc	10000
	iastore
	aload	0
	ldc	5
	ldc	100000
	iastore
	aload	0
	ldc	6
	ldc	1000000
	iastore
	aload	0
	ldc	7
	ldc	10000000
	iastore
	aload	0
	ldc	8
	ldc	100000000
	iastore
	aload	0
	ldc	9
	ldc	1000000000
	iastore
	aload	0
	ldc	0
	iaload
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	aload	0
	ldc	1
	iaload
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	aload	0
	ldc	2
	iaload
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	aload	0
	ldc	3
	iaload
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	aload	0
	ldc	4
	iaload
	invokestatic	CSXLib/printInt(I)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	aload	0
	ldc	5
	iaload
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	aload	0
	ldc	6
	iaload
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	aload	0
	ldc	7
	iaload
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	aload	0
	ldc	8
	iaload
	invokestatic	CSXLib/printInt(I)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	aload	0
	ldc	9
	iaload
	invokestatic	CSXLib/printInt(I)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	aload	1
	ldc	0
	ldc	0
	bastore
	aload	1
	ldc	1
	ldc	1
	bastore
	aload	1
	ldc	0
	baload
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\t"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	aload	1
	ldc	1
	baload
	invokestatic	CSXLib/printBool(Z)V
	ldc	"\n"
	invokestatic	CSXLib/printString(Ljava/lang/String;)V
	return
	.limit	stack  25
	.limit	locals  2
	.end	method
