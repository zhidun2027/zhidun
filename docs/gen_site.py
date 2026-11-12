# -*- coding: utf-8 -*-
# Regenerate the docs site from ../technical-manual.md:  python3 docs/gen_site.py
import re, os
from markdown_it import MarkdownIt

HERE = os.path.dirname(os.path.abspath(__file__))
SRC = os.path.join(HERE, "..", "technical-manual.md")
OUT = HERE
os.makedirs(os.path.join(OUT, "assets"), exist_ok=True)

md_text = open(SRC, encoding="utf-8").read()
# drop single-page anchors so they don't show as text in the multi-page split
md_text = re.sub(r'^<a id="[^"]*"></a>\s*$', '', md_text, flags=re.M)

md = MarkdownIt("commonmark", {"html": False, "linkify": False, "typographer": False})
md.enable("table")

# split by "## Part N" / "## Appendix"
lines = md_text.split("\n")
bounds = []
for i, l in enumerate(lines):
    if re.match(r'^## (Part \d+|Appendix)\b', l):
        bounds.append(i)
front = "\n".join(lines[:bounds[0]])
# drop a single-page table of contents (the multi-page uses the sidebar + cards)
front = re.split(r'^## (?:Contents|Table of Contents)\s*$', front, flags=re.M)[0]
chunks = []
for k, b in enumerate(bounds):
    e = bounds[k+1] if k+1 < len(bounds) else len(lines)
    chunks.append("\n".join(lines[b:e]))

# nav: (file, number, short title, card description)
NAV = [
    ("index.html", "·",  "Overview",                 "Document notes and links to every part"),
    ("01.html",    "01", "Background & Overview",     "What it is, the model, the three-layer architecture, data flow"),
    ("02.html",    "02", "PC Hardening Tool",         "exe4j wrapper, the 6 hardening steps, cls_key, 540-day validity, encryption"),
    ("03.html",    "03", "config.dll Shell Resources","decryption, shell payload structure, shell class list"),
    ("04.html",    "04", "Runtime Shell",             "StubApp, native engine, anti-debug and detection checklist"),
    ("05.html",    "05", "Cipher Specification",      "stream cipher definition, pseudocode, key list"),
    ("06.html",    "06", "Version Fingerprint",       "fingerprint table, how to tell versions apart"),
    ("07.html",    "07", "Conclusions & Assessment",  "strengths and weaknesses, unpacking points, feasibility"),
    ("08.html",    "08", "Appendix",                  "file list, key table, class/function index, glossary, reproduction"),
]

def dedent_headings(mdchunk):
    # ## -> #, ### -> ## ... so each page's heading becomes h1
    out = []
    for l in mdchunk.split("\n"):
        m = re.match(r'^(#{2,6}) (.*)$', l)
        if m:
            out.append("#" * (len(m.group(1)) - 1) + " " + m.group(2))
        else:
            out.append(l)
    return "\n".join(out)

def render_md(mdchunk):
    return md.render(dedent_headings(mdchunk))

def sidebar(active):
    items = []
    for i, (fn, num, label, _) in enumerate(NAV):
        cls = ' class="active"' if i == active else ''
        items.append(f'<a href="{fn}"{cls}><span class="n">{num}</span><span class="t">{label}</span></a>')
    return "\n".join(items)

def prevnext(i):
    parts = []
    if i > 0:
        fn, num, label, _ = NAV[i-1]
        parts.append(f'<a class="pn prev" href="{fn}"><span>← Previous</span><b>{num} {label}</b></a>')
    else:
        parts.append('<span></span>')
    if i < len(NAV)-1:
        fn, num, label, _ = NAV[i+1]
        parts.append(f'<a class="pn next" href="{fn}"><span>Next →</span><b>{num} {label}</b></a>')
    else:
        parts.append('<span></span>')
    return '<nav class="prevnext">' + "".join(parts) + '</nav>'

PAGE = '''<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>{title} · zhidun 3.3.7 — Technical Manual</title>
<link rel="stylesheet" href="assets/site.css">
</head>
<body>
<button id="navtoggle" aria-label="Menu">☰ Menu</button>
<div class="layout">
  <aside class="sidebar">
    <a class="brand" href="index.html">
      <span class="logo">🛡️</span>
      <span><b>zhidun 3.3.7</b><small>Reverse-engineering manual</small></span>
    </a>
    <nav class="nav">
{nav}
    </nav>
    <div class="side-foot">CFR · dex2jar · Ghidra decompilation<br>every finding cites file:line</div>
  </aside>
  <main class="content">
    <div class="inner">
{body}
{prevnext}
    </div>
  </main>
</div>
<script>
  var t=document.getElementById('navtoggle'),s=document.querySelector('.sidebar');
  t&&t.addEventListener('click',function(){{document.body.classList.toggle('nav-open');}});
</script>
</body>
</html>
'''

# home cards
cards = ['<div class="cards">']
for fn, num, label, desc in NAV[1:]:
    cards.append(f'''<a class="card" href="{fn}">
      <span class="card-n">{num}</span>
      <span class="card-t">{label}</span>
      <span class="card-d">{desc}</span>
    </a>''')
cards.append('</div>')
index_body = render_md(dedent_headings(front)) + '<h2>Sections</h2>' + "\n".join(cards)

# write pages
pages = [("index.html", 0, "Overview", index_body)]
for i, ch in enumerate(chunks):
    fn, num, label, _ = NAV[i+1]
    pages.append((fn, i+1, label, render_md(ch)))

for fn, idx, title, body in pages:
    htmlout = PAGE.format(title=title, nav=sidebar(idx), body=body, prevnext=prevnext(idx))
    open(os.path.join(OUT, fn), "w", encoding="utf-8").write(htmlout)

print("Generated pages:", ", ".join(p[0] for p in pages))
