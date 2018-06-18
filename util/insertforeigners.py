import random	
f = open("insertforeigners.sql", "w")
with open("foreigners.txt", "w") as foreigners:
	for line in foreigners:
		line = line.replace('\n', '')
		name = line.split(' ')[0]
		suraname = line.split(' ')[1]
		y= random.randint(1950, 2001)
		m = random.randint(1, 12)
		d = random.randint(1, 28)
		s = "INSERT INTO pacjenci (imie, nazwisko, nr_paszportu, data_urodzenia, plec) VALUES(\'"+name+"\', \'"+surname+"\', \'GBR"+str(y)[-2:]+str(m)+str(d)+"\', \'"+str(y)+"-"+str(m)+"-"+str(d)+"\', \'M\');\n"
		f.write(s)
with open("foreigners.txt", "w") as foreigners:
	for line in foreigners:
		line = line.replace('\n', '')
		name = line.split(' ')[0]
		suraname = line.split(' ')[1]
		y= random.randint(1950, 2001)
		m = random.randint(1, 12)
		d = random.randint(1, 28)
		s = "INSERT INTO pacjenci (imie, nazwisko, nr_paszportu, data_urodzenia, plec) VALUES(\'"+name+"\', \'"+surname+"\', \'GBR"+str(y)[-2:]+str(m)+str(d)+"\', \'"+str(y)+"-"+str(m)+"-"+str(d)+"\', \'M\');\n"
		f.write(s)
f.close()
