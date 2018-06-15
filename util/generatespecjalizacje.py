import random
f = open("insertspec.sql", "w")
arr = list(range(1,23))
with open("lekarze.txt") as doctors:
	for line in doctors:
		line = line.replace('\n', '')
		s = "INSERT INTO lekarze_specjalizacje VALUES("+line+", "
		random.shuffle(arr)
		for i in range(0,random.randint(1,3)):
			f.write(s+str(arr[i])+");\n")
f.close()

