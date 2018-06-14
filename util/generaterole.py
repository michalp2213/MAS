import random
f = open("insertrole.sql", "w")
for i in range(1, 81):
	s = "INSERT INTO pracownicy_role VALUES("
	if i % 2 == 0:
		if random.randint(0,1) == 0:
			s+="1, "
		else:
			s+="4, "
	else:
		s+=str(random.randint(5,8))+", "
	s+=str(i)+");\n"
	f.write(s)
f.close()
