{
  lib,
  python3Packages,
  fetchFromGitHub,
}:
python3Packages.buildPythonApplication rec {
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
    (hyrule.overridePythonAttrs (_: rec {
      version = "0.7.0";
      src = fetchPypi {
        pname = "hyrule";
        inherit version;
        hash = "sha256-Oks1aKv+FtFoleWEwNuEwSUdJY/iNLqG8HKQa5BUXOg=";
      };
      doCheck = false;
    }))
    pygls
    setuptools
    (toolz.overridePythonAttrs (_: rec {
      version = "0.12.1";
      src = fetchPypi {
        pname = "toolz";
        inherit version;
        hash = "sha256-7Mo0JmSJPxd6E9rA5rQcvYrCWjWOXyFTFtQ+IQAiT00=";
      };
    }))
  ];

  pythonImportsCheck = [
    "hyuga"
  ];

  meta = {
    description = "Yet Another Hy Language Server";
    homepage = "https://github.com/sakuraiyuta/hyuga";
    license = lib.licenses.mit;
    mainProgram = "hyuga";
  };
}
