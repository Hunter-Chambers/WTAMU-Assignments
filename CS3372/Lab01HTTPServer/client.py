#!/usr/bin/env python3
import socket
import sys

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((socket.gethostname(), int(sys.argv[1])))

s.sendall(b"GET / HTTP/1.1")

msg = s.recv(1024)
print(msg.decode("utf-8"))
msg = s.recv(1024)
print(msg.decode("utf-8"))
msg = s.recv(1024)
print(msg.decode("utf-8"))

s.close()
