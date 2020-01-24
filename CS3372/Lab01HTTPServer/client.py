#!/usr/bin/env python3
import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((socket.gethostname(), 8080))

s.sendall(b"Hello, World!")

msg = s.recv(1024)
print(msg.decode("utf-8"))

s.close()
