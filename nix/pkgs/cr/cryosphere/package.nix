{
  astalPackages,
  python3Packages,
  astal-py,
  gobject-introspection,
}:
python3Packages.buildPythonApplication {
  pname = "cryosphere";
  version = "0.0.1";
  pyproject = true;

  src = ../../../../.;

  dependencies = with python3Packages; [
    astal-py
    hy
  ];

  buildInputs = with astalPackages; [
    io
    astal3
    astal4
    apps
    battery
    bluetooth
    cava
    hyprland
    mpris
    network
    notifd
    tray
    wireplumber

    gobject-introspection
  ];
}
