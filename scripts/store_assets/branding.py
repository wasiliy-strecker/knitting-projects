"""Render original vector branding and fictional project illustrations for isolated previews."""

import shutil
import subprocess
from pathlib import Path

import cairo
import gi

gi.require_version("Rsvg", "2.0")
from gi.repository import Rsvg

ROOT = Path(__file__).resolve().parents[2]


def render(source, target, size):
    width, height = map(int, size.split("x"))
    surface = cairo.ImageSurface(cairo.FORMAT_ARGB32, width, height)
    viewport = Rsvg.Rectangle()
    viewport.x, viewport.y, viewport.width, viewport.height = 0, 0, width, height
    Rsvg.Handle.new_from_file(str(source)).render_document(
        cairo.Context(surface), viewport
    )
    surface.write_to_png(str(target))


def main():
    branding = ROOT / "assets/branding"
    branding.mkdir(parents=True, exist_ok=True)
    svg = """<svg xmlns="http://www.w3.org/2000/svg" width="1024" height="1024" viewBox="0 0 256 256">
<rect width="256" height="256" rx="48" fill="#2e5551"/>
<path d="M58 56L195 194M74 45L210 183" stroke="#e8c9a1" stroke-width="9" stroke-linecap="round"/>
<path d="M58 92H179V205H58Z" fill="#e0eee8"/>
<g fill="none" stroke="#70a398" stroke-width="6" stroke-linecap="round">
<path d="M70 107l9 12 9-12m9 0 9 12 9-12m9 0 9 12 9-12m9 0 9 12 9-12M70 134l9 12 9-12m9 0 9 12 9-12m9 0 9 12 9-12m9 0 9 12 9-12M70 161l9 12 9-12m9 0 9 12 9-12m9 0 9 12 9-12m9 0 9 12 9-12M70 188l9 12 9-12m9 0 9 12 9-12m9 0 9 12 9-12m9 0 9 12 9-12"/>
</g><circle cx="185" cy="76" r="36" fill="#426d68" stroke="#f8f5f1" stroke-width="6"/>
<path d="M168 76l11 12 23-26" fill="none" stroke="#f8f5f1" stroke-width="7" stroke-linecap="round" stroke-linejoin="round"/>
</svg>"""
    (branding / "icon.svg").write_text(svg, encoding="utf-8")
    for size in (44, 50, 150, 256, 300, 1024):
        name = {
            44: "Square44x44Logo.png",
            50: "StoreLogo.png",
            150: "Square150x150Logo.png",
            300: "store-icon-300.png",
        }.get(size, f"icon-{size}.png")
        render(branding / "icon.svg", branding / name, f"{size}x{size}")
    subprocess.run(
        [
            "convert",
            str(branding / "icon-256.png"),
            "-define",
            "icon:auto-resize=256,128,64,48,32,16",
            str(branding / "icon.ico"),
        ],
        check=True,
    )
    shutil.copy2(
        branding / "icon-256.png",
        ROOT / "src/main/resources/de/appfabrik/strickprojekt/icon-256.png",
    )
    photos = ROOT / "assets/store_demo/images"
    photos.mkdir(parents=True, exist_ok=True)
    for name, base, light, dark in (
        ("sage", "#92aa8c", "#c8d6bb", "#61785d"),
        ("lilac", "#ad92be", "#d5c4df", "#7a648d"),
        ("rose", "#c99292", "#e8bdb2", "#996969"),
    ):
        stitches = []
        for row in range(9):
            for column in range(10):
                x, y = 95 + column * 24, 185 + row * 30
                stitches.append(
                    f'<path d="M{x} {y}Q{x + 1} {y + 16} {x + 10} {y + 23}Q{x + 20} {y + 16} {x + 20} {y}"/>'
                )
        strands = [
            f'<path d="M390 {210 + i * 17}Q570 {350 + i * 10} 770 {190 + i * 20}"/>'
            for i in range(15)
        ]
        content = f'''<svg xmlns="http://www.w3.org/2000/svg" width="800" height="600" viewBox="0 0 800 600">
<rect width="800" height="600" fill="#f4ede4"/>
<ellipse cx="446" cy="482" rx="300" ry="35" fill="#e3d9ce"/>
<path d="M400 130L700 490M410 110L725 465" stroke="#be9774" stroke-width="12" stroke-linecap="round"/>
<rect x="85" y="170" width="260" height="290" rx="16" fill="{base}"/>
<g fill="none" stroke="{light}" stroke-width="7" stroke-linecap="round">{"".join(stitches)}</g>
<ellipse cx="557" cy="326" rx="169" ry="143" fill="{base}"/>
<clipPath id="skein"><ellipse cx="557" cy="326" rx="165" ry="139"/></clipPath>
<g clip-path="url(#skein)" fill="none" stroke="{dark}" stroke-width="7">{"".join(strands)}</g>
<g clip-path="url(#skein)" fill="none" stroke="{light}" stroke-width="5"><path d="M460 165Q480 290 710 420M440 175Q460 310 690 450M423 188Q442 330 670 475M410 200Q422 350 650 490"/></g>
<path d="M600 463Q650 510 745 499Q782 488 759 468" fill="none" stroke="{base}" stroke-width="7" stroke-linecap="round"/>
</svg>'''
        (photos / f"{name}.svg").write_text(content, encoding="utf-8")
        render(photos / f"{name}.svg", photos / f"{name}.png", "800x600")
    subprocess.run(
        [
            "convert",
            str(photos / "lilac.png"),
            "-background",
            "#f4ede4",
            "-alpha",
            "remove",
            "-quality",
            "92",
            str(photos / "lilac.jpg"),
        ],
        check=True,
    )


if __name__ == "__main__":
    main()
