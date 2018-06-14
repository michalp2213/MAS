import random
def getDate(pesel):
	date = "\'"+pesel[4:6]+"."
	day = pesel[2:4]
	if int(day) > 20:
		date+=str(int(day)-20 if int(day)-20>9 else "0"+str(int(day)-20))+".20"+pesel[0:2]+"\'"
	else:
		date+=str(day)+".19"+pesel[0:2]+"\'"
	return date
	
f = open("insertp.sql", "w")
firsts = []
lasts = []
n = 0
m = 0
with open("firstm.txt") as names:
	for line in names:
		firsts.append(line.replace("\n", ""))
		n = n+1
with open("last.txt") as names:
	for line in names:
		lasts.append(line.replace("\n", ""))
		m = m+1
with open("peselm.txt") as pesels:
	for line in pesels:
		line = line.replace("\n", "")
		f.write("INSERT INTO pracownicy(imie, nazwisko, pesel) VALUES(\'"+firsts[random.randint(0,n-1)]+"\', \'"+lasts[random.randint(0, m-1)]+"\', \'"+line+"\');\n")
firsts = []
lasts = []
n = 0
m = 0
with open("firstf.txt") as names:
	for line in names:
		line = line.replace("\n", "")
		firsts.append(line)
		n = n+1
with open("last.txt") as names:
	for line in names:
		line = line.replace("\n", "")
		if line[-1:] == "i" :
			line = line[:-1]+"a"
		lasts.append(line)
		m = m+1
with open("peself.txt") as pesels:
	for line in pesels:
		line = line.replace("\n", "")
		f.write("INSERT INTO pracownicy(imie, nazwisko, pesel) VALUES(\'"+firsts[random.randint(0,n-1)]+"\', \'"+lasts[random.randint(0, m-1)]+"\', \'"+line+"\');\n")
f.close()
