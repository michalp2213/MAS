import random
f = open("insertoperacje.sql", "w")
with open("operacje.txt") as operations:
	for line in operations:
		line = line.replace('\n', '')
		line = line[0].upper()+line[1:]
		s = "INSERT INTO cele_wizyty(nazwa) VALUES('"+line+"');\n"
		f.write(s)
f.close()

