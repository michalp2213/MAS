import random
f = open("insertchoroby.sql", "w")
with open("choroby.txt") as diseases:
	for line in diseases:
		line = line.replace('\n', '')
		line = line[0].upper()+line[1:]
		s = "INSERT INTO wydarzenia_medyczne(nazwa) VALUES('"+line+"');\n"
		f.write(s)
f.close()

