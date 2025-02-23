{
  lib,
  fetchFromGitHub,
  python313Packages,
  gengir,
}:
python313Packages.buildPythonPackage rec {
  pname = "astal-py";
  version = "0.1.0";
  pyproject = true;

  src = fetchFromGitHub {
    owner = "0x6e6174";
    repo = "astal";
    rev = "2e1ba8f7a271a2d38c492309a5404c3200273dd3";
    hash = "sha256-RvAgyB2mh4Q+LgMF0a88f6Ta0E5jJ5IF6yV2EHbFHOw=";
  };
  sourceRoot = "${src.name}/lang/python";

  build-system = with python313Packages; [ poetry-core ];

  dependencies = with python313Packages; [
    gengir
    pygobject3
  ];

  meta = with lib; {
    description = "Astal Python bindings";
    homepage = "https://aylur.github.io/astal";
    changelog = "https://github.com/Aylur/astal/blob/main/CHANGELOG.md";
    license = licenses.lgpl21Only;
  };
}
