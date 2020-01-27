#!/usr/bin/env python3
import socket
import sys

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((socket.gethostname(), int(sys.argv[1])))

s.sendall(b"GET /" + sys.argv[2].encode() + b" HTTP/1.1\r\n")

msg = s.recv(1024).decode()
print(msg)
msg = s.recv(1024).decode()
print(msg)
msg = s.recv(1024).decode()
print(msg)

s.close()
