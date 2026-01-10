# ...existing code...
import os, re
from pathlib import Path

ROOT = Path('.').resolve()
out = ROOT / "README.md"

dirs = []
for d in ROOT.iterdir():
    if d.is_dir():
        m = re.match(r'^(\d+)', d.name)
        if m:
            num = int(m.group(1))
            dirs.append((num, d.name))
dirs.sort(key=lambda x: (x[0], x[1]))

lines = [
    "# Leetcode\n\n",
    "Solving DSA problems with explanations for \"why\" and \"how\".\n\n",
    f"Total problems: **{len(dirs)}**\n\n",
    "## Problems (sorted by LeetCode problem number)\n\n---\n\n"
]

for idx, (num, name) in enumerate(dirs, start=1):
    safe = name.replace(' ', '%20')
    base = f"./{safe}/"
    lines.append(f"{idx}. [{name}]({base})\n\n")
    sol_path = ROOT / name / "Solution.java"
    hw_path = ROOT / name / "how_why.md"

    # Indent sub-items enough to remain nested even after the list index hits 3+ digits.
    sub_indent = ' ' * max(4, len(str(idx)) + 2)

    if sol_path.exists():
        lines.append(f"{sub_indent}- [Solution.java]({base}Solution.java)\n")
    if hw_path.exists():
        lines.append(f"{sub_indent}- [how_why.md]({base}how_why.md)\n")
    lines.append("\n")

out.write_text(''.join(lines), encoding='utf-8')
print(f"Wrote {out} ({len(dirs)} problems)")
# ...existing code...