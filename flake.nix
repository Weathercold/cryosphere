{
  description = "Weathercold's Desktop Shell";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    ags = {
      url = "github:aylur/ags";
      inputs.nixpkgs.follows = "nixpkgs";
    };
    dots = {
      # url = "github:ferdinand-beyer/dots";
      url = "github:SughiY/dots";
      flake = false;
    };

    flake-parts = {
      url = "github:hercules-ci/flake-parts";
      inputs.nixpkgs-lib.follows = "nixpkgs";
    };
    clj-nix = {
      url = "github:jlesquembre/clj-nix";
      inputs.nixpkgs.follows = "nixpkgs";
    };
  };

  outputs =
    {
      self,
      nixpkgs,
      ags,
      flake-parts,
      ...
    }@inputs:
    flake-parts.lib.mkFlake { inherit inputs; } {
      systems = [ "x86_64-linux" ];

      perSystem =
        {
          self',
          inputs',
          pkgs,
          ...
        }:
        {
          packages = rec {
            default = cryosphere;
            cryosphere = pkgs.callPackage ./nix/pkgs/cr/cryosphere/package.nix {
              agsPackages = inputs'.ags.packages;
              agsLib = ags.lib;
            };

            dots = pkgs.callPackage ./nix/pkgs/do/dots/package.nix {
              inherit (inputs'.clj-nix.packages) mk-deps-cache;
              src = inputs.dots;
            };
          };

          formatter = pkgs.nixfmt-rfc-style;

          devShells = rec {
            default = cryosphere;
            cryosphere = pkgs.mkShell {
              buildInputs = with pkgs; [
                # Include all Astal libraries
                inputs'.ags.packages.agsFull
                self'.packages.dots
                nil
                nixfmt-rfc-style
                nodejs
                nodemon
                (pkgs.writeShellApplication {
                  name = "dots-deps-lock";
                  runtimeInputs = [ inputs'.clj-nix.packages.deps-lock ];
                  text = ''
                    cd nix/pkgs/do/dots
                    deps-lock --deps-include "${inputs.dots}/deps.edn"
                  '';
                })
              ];
            };
          };
        };
    };
}
