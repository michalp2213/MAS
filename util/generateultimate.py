import random

f1 = open("insertwizyty_odbyte.sql", "w")
f2 = open("insertskierowania.sql", "w")
f3 = open("insertankiety.sql", "w")
f4 = open("inserthistoria.sql", "w")
f5 = open("insertwizyty_planowane.sql", "w")
doctors = []
m = "2002-11-22"
with open("lekarze2.txt", "r") as d:
    for line in d:
        line = line.replace('\n', '')
        temp = line.split(' ')
        temp[1] = max(temp[1], m)
        doctors.append(temp)
count = 0
for j in range(1, 121):
    random.shuffle(doctors)
    for i in range(0, random.randint(0, 3)):
        count += 1
        s2 = "INSERT INTO skierowania VALUES (DEFAULT, " + str(count) + ", " + str(random.randint(1, 22)) + ", " + str(
            random.randint(1, 92)) + ", NULL);\n"
        s1 = "INSERT INTO wizyty_odbyte VALUES (DEFAULT, " + str(j) + ", "
        date = "DATE \'" + doctors[i][
            1] + "\' + INTERVAL\'" + str(random.randint(1, 12)) + " MONTHS " + str(random.randint(1, 28)) + " DAYS\'"
        f1.write(s1 + str(doctors[i][0]) + ", " + str(random.randint(1, 92)) + ", " + doctors[i][
            random.randint(2, len(doctors[i]) - 1)] + ", " + date + ", INTERVAL \'" + str(
            random.randint(15, 60)) + " MINUTES\');\n")
        f2.write(s2)
        s3 = "INSERT INTO ankiety_lekarze VALUES (DEFAULT, " + doctors[i][0] + ", " + date
        for q in range(0, 4):
            a = random.randint(0, 5)
            if a == 0:
                s3 += ", NULL"
            else:
                s3 += ", " + str(a)
        s3 += ");\n"
        s4 = "INSERT INTO historia_medyczna VALUES (" + str(j) + ", " + str(random.randint(1, 100)) + ", " + str(
            count) + ", " + date + ", " + date + "+INTERVAL \'" + str(random.randint(1, 5)) + " YEARS " + str(
            random.randint(1, 12)) + " MONTHS " + str(random.randint(1, 28)) + " DAYS\');\n"
        f3.write(s3)
        f4.write(s4)
        f5.write("INSERT INTO terminarz VALUES(" + str(j) + ", " + str(random.randint(1, 92)) + ", " + str(
            random.randint(1, 22)) + ", (DATE\'" +str(random.randint(2019, 2027)) + "-" + str(
            random.randint(1, 12)) + "-" + str(random.randint(1, 28)) + "\')::TIMESTAMP);\n")

f1.close()
f2.close()
f3.close()
f4.close()
f5.close()
