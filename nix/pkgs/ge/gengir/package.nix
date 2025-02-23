{
  lib,
  rustPlatform,
  fetchPypi,
  python3Packages,
}:
python3Packages.buildPythonPackage rec {
  pname = "gengir";
  version = "unstable-2022-09-09";

  src = fetchPypi {
    pname = "gengir";
    version = "1.0.2";
    hash = "sha256-cM+6HZ9JHuU31croD8Kdh4bQl9AYlXHOufd4D7TUtHU=";
  };

  cargoDeps = rustPlatform.fetchCargoVendor {
    inherit pname version src;
    hash = "sha256-hl48z6uDa4uywbeuvQDUHYvq5Y9/d9eS0R9aITtaNZ4=";
  };

  build-system = with python3Packages; [ poetry-core ];

  meta = {
    description = "Genuine* autocompletion for your PyGObject code!";
    homepage = "https://github.com/santiagocezar/gengir";
    license = lib.licenses.mit;
    mainProgram = "gengir";
  };
}
