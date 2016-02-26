import socket

s = socket.socket()
host = socket.gethostname()
port = 9091

s.connect((host, port))

s.send("SELL NAHAL01 1023 1332\n")
print s.recv(1024)