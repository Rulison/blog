#!/usr/bin/python
""" Run the specified blog file and generate a html file reporting
    the convergence graph and the generated CBN.

    @author rbharath
    @date September 16th, 2012
"""

import os
import sys
import cgi
import subprocess

usage = "Accepts exactly one argument, a blog file!"
model = "example/figures/model"
error = "example/figures/error"
hist = "example/figures/hist"
out = ""

if len(sys.argv) != 2:
    print usage
    print sys.argv
    sys.exit(1)
blog = sys.argv[1]
parts = blog.split(".")
name = os.path.basename(parts[0])
if len(parts) != 2 or (parts[1] != "blog" and parts[1] != "dblog"):
    print usage
    print parts
    sys.exit(1)

fname = name + ".html"
f = open(fname, "w")
command = ["python", "run_examples.py", "-r", "-e", blog]
subprocess.call(command)
model_name = None
error_name = None

for m in os.listdir(model):
    if name in str(m):
        model_name = m
        break
for m in os.listdir(error):
    if name in str(m):
        error_name = m
        break

hist_names = []
for m in os.listdir(hist):
    if name in str(m):
        hist_names.append(m)

if model_name is not None:
    model_path = os.path.join(model, model_name)
    print "model_path: " + model_path
if error_name is not None:
    error_path = os.path.join(error, error_name)
    print "error_path: " + error_path
hist_paths = []
for hist_name in hist_names:
    hist_paths.append(os.path.join(hist, hist_name))

out += "<html>\n"
out += "<body>\n"
out += "<h1>\n"
out += blog + "\n"
out += "</h1>\n"
out += "<h2>\n"
out += "CBN: \n"
out += "</h2>\n"
if model_name is not None:
    out += "<p>\n"
    out += "<img src=\"" + cgi.escape(model_path) + "\"/>\n"
    out += "</p>\n"
out += "<h2>\n"
out += "Convergence: \n"
out += "</h2>\n"
if error_name is not None:
    out += "<p>\n"
    out += "<img src=\"" + cgi.escape(error_path) + "\"/>\n"
    out += "</p>\n"
out += "<h2>\n"
out += "Histograms: \n"
out += "</h2>\n"
out += "<div style=\"width:1634px\">\n"
for hist_path in hist_paths:
    out += "<img src=\"" + cgi.escape(hist_path) + "\" style=\"float:left;width:49%\"/>\n"
out += "</div>\n"
out += "<h2>\n"
out += "Run Summary: \n";
out += "</h2>\n"
f2 = open('report', 'r');
for line in f2:
    out += "<p>" + line + "</p>\n"
out += "</body>\n"
out += "</html>\n"
f.write(out)
f.close()
if sys.platform == 'darwin':
    command = ["open", fname]
    subprocess.call(command)
    pass
elif sys.platform.startswith('linux'):
    command = ["xdg-open", fname]
    subprocess.call(command)