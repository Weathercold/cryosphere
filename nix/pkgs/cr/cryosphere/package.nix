{
  pkgs,
  agsLib,
  buildNpmPackage,
  ags,
}:

let
  bin = buildNpmPackage {
    pname = "cryosphere";
    version = "unstable-0.0.1";

    src = ../../../../.;

    npmDepsHash = "sha256-lSdwdVOef9t7RoGEpPwlXPzj24kwFcUhqCAdwLkNfuQ=";

    installPhase = ''
      mkdir -p "$out"
      cp -r assets bin node_modules "$out"
    '';
  };
in

agsLib.bundle {
  inherit pkgs;
  name = "cryosphere";

  src = bin;

  entry = "./bin/cryosphere/app.jsx";
  gtk4 = true;

  # Additional libraries and executables to add to gjs' runtime
  extraPackages = [
    ags.packages.battery
  ];
}
