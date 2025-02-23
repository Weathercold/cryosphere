{
  agsPackages,
  python313Packages,
  astal-py,
}:
python313Packages.buildPythonApplication {
  pname = "cryosphere";
  version = "unstable-0.0.1";
  pyproject = true;

  src = ../../../../.;

  dependencies = with python313Packages; [
    astal-py
    hy
  ];

  buildInputs = with agsPackages; [
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
  ];
}
