{
  lib,
  python3Packages,
  fetchFromGitHub,
}:
python3Packages.buildPythonPackage {
  pname = "hyrepl";
  version = "unstable-2024-11-30";
  pyproject = true;

  src = fetchFromGitHub {
    owner = "masatoi";
    repo = "HyREPL";
    rev = "ac4208bd12771b6f1ae09c5de2f64c5d6e1774ff";
    hash = "sha256-HUvr9XaQGGk8z0nBeEfZuM/s2OO6ww07lN5RXXZT18c=";
  };

  build-system = with python3Packages; [
    setuptools
    wheel
  ];

  dependencies = with python3Packages; [
    hy
    hyrule
    pytest
    toolz
  ];

  pythonImportsCheck = [
    "HyREPL"
  ];

  meta = with lib; {
    description = "NREPL for Hy";
    homepage = "https://github.com/masatoi/HyREPL";
    license = licenses.mit;
    mainProgram = "hyrepl";
  };
}
