{
  lib,
  python3Packages,
  fetchFromGitHub,
}:
python3Packages.buildPythonPackage rec {
  pname = "hyuga";
  version = "1.0.0";
  pyproject = true;

  src = fetchFromGitHub {
    owner = "sakuraiyuta";
    repo = "hyuga";
    rev = version;
    hash = "sha256-7TLWx+t9VE1LcjH3z0XGHSeR1kcYy2EjPmvI1fUoilM=";
  };

  build-system = with python3Packages; [ poetry-core ];

  dependencies = with python3Packages; [
    hy
    hyrule
    pygls
    setuptools
    toolz
  ];

  pythonImportsCheck = [
    "hyuga"
  ];

  meta = with lib; {
    description = "Yet Another Hy Language Server";
    homepage = "https://github.com/sakuraiyuta/hyuga";
    license = licenses.mit;
    mainProgram = "hyuga";
  };
}
