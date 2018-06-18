import random
def randomstr():
	s = ""
	for i in range(0,5):
		s+=str(random.randint(0,9))
	return s
f = open("insertforeigners.sql", "w")
with open("foreigners.txt", "r") as foreigners:
	for line in foreigners:
		line = line.replace('\n', '')
		name = line.split(' ')[0]
		surname = line.split(' ')[1]
		y= random.randint(1950, 2001)
		m = random.randint(1, 12)
		d = random.randint(1, 28)
		s = "INSERT INTO pacjenci (imie, nazwisko, nr_paszportu, data_urodzenia, plec) VALUES(\'"+name+"\', \'"+surname+"\', \'GBR"+str(y+m+d)+randomstr()+"\', \'"+str(y)+"-"+str(m)+"-"+str(d)+"\', \'M\');\n"
		f.write(s)
with open("foreignersf.txt", "r") as foreigners:
	for line in foreigners:
		line = line.replace('\n', '')
		name = line.split(' ')[0]
		suraname = line.split(' ')[1]
		y= random.randint(1950, 2001)
		m = random.randint(1, 12)
		d = random.randint(1, 28)
		s = "INSERT INTO pacjenci (imie, nazwisko, nr_paszportu, data_urodzenia, plec) VALUES(\'"+name+"\', \'"+surname+"\', \'GBR"+str(y+m+d)+randomstr()+"\', \'"+str(y)+"-"+str(m)+"-"+str(d)+"\', \'F\');\n"
		f.write(s)

