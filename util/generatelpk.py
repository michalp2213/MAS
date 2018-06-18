import random
f = open("insertlpk.sql", "w")
doctors = []
with open("lekarze.txt") as docs:
	for line in docs:
		doctors.append(line.replace('\n',''))
with open("pacjenci.txt") as p:
	for line in p:
		line = line.replace('\n','')
		s = ""
		if line[1] == "	":
			s = "INSERT INTO pacjenci_lpk VALUES("+line[0]+", "+doctors[random.randint(0, len(doctors)-1)]+", "
			k ="DATE \'2000-"+str(random.randint(1,12))+"-"+str(random.randint(1,28))+"\' + INTERVAL \'" + str(random.randint(1, 17)) + " YEARS\'"
			s+=k+", "+k+"+ INTERVAL \'"+str(random.randint(3, 12))+" MONTHS\');\n"
		else:
			s = "INSERT INTO pacjenci_lpk VALUES("+line[:2]+", "+doctors[random.randint(0, len(doctors)-1)]+", "
			k ="DATE \'2000-"+str(random.randint(1,12))+"-"+str(random.randint(1,28))+"\' + INTERVAL \'" + str(random.randint(1, 17)) + " YEARS\'"
			s+=k+", "+k+"+ INTERVAL \'"+str(random.randint(3, 12))+" MONTHS\');\n"
		f.write(s)
f.close()
