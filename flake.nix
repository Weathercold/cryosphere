{
  description = "Weathercold's GTK Shell";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    astal = {
      url = "github:Aylur/astal";
      inputs.nixpkgs.follows = "nixpkgs";
    };

    flake-parts = {
      url = "github:hercules-ci/flake-parts";
      inputs.nixpkgs-lib.follows = "nixpkgs";
    };
  };

  outputs =
    { flake-parts, ... }@inputs:
    flake-parts.lib.mkFlake { inherit inputs; } {
      systems = [ "x86_64-linux" ];

      perSystem =
        {
          self',
          inputs',
          pkgs,
          ...
        }:

        let
          inherit (pkgs.lib) singleton;

          finalPkgs = pkgs.extend (
            _: prev: {
              pythonPackagesExtensions =
                prev.pythonPackagesExtensions
                ++ singleton (
                  _: pyPrev: {
                    hyrule = pyPrev.hyrule.overridePythonAttrs (_: rec {
                      version = "0.7.0";
                      src = prev.fetchPypi {
                        pname = "hyrule";
                        inherit version;
                        hash = "sha256-Oks1aKv+FtFoleWEwNuEwSUdJY/iNLqG8HKQa5BUXOg=";
                      };
                      doCheck = false;
                    });
                    toolz = pyPrev.toolz.overridePythonAttrs (_: rec {
                      version = "0.12.1";
                      src = prev.fetchPypi {
                        pname = "toolz";
                        inherit version;
                        hash = "sha256-7Mo0JmSJPxd6E9rA5rQcvYrCWjWOXyFTFtQ+IQAiT00=";
                      };
                    });
                  }
                );
            }
          );

          python = finalPkgs.python3.withPackages (
            pythonPackages: with pythonPackages; [
              self'.packages.astal-py
              hy
              self'.packages.hyrepl
              self'.packages.hyuga
            ]
          );
        in

        {
          packages = rec {
            default = cryosphere;
            cryosphere = finalPkgs.callPackage ./nix/pkgs/cr/cryosphere/package.nix {
              astalPackages = inputs'.astal.packages;
              inherit astal-py;
            };
            astal-py = finalPkgs.callPackage ./nix/pkgs/as/astal-py/package.nix {
              inherit gengir;
            };
            gengir = finalPkgs.callPackage ./nix/pkgs/ge/gengir/package.nix { };
            hyrepl = finalPkgs.callPackage ./nix/pkgs/hy/hyrepl/package.nix { };
            hyuga = finalPkgs.callPackage ./nix/pkgs/hy/hyuga/package.nix { };
          };

          formatter = finalPkgs.nixfmt-rfc-style;

          devShells = rec {
            default = cryosphere;
            cryosphere = finalPkgs.mkShell {
              buildInputs = with finalPkgs; [
                nil
                nixfmt-rfc-style
                python
                sass
              ];

              inputsFrom = [ self'.packages.cryosphere ];

              shellHook = ''
                export PATH="${python}/bin:$PATH"
              '';
            };
          };
        };
    };
}
