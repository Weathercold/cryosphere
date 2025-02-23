{
  description = "Weathercold's Desktop Shell";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    ags = {
      url = "github:aylur/ags";
      inputs.nixpkgs.follows = "nixpkgs";
    };

    flake-parts = {
      url = "github:hercules-ci/flake-parts";
      inputs.nixpkgs-lib.follows = "nixpkgs";
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
              inherit astal-py;
            };
            astal-py = pkgs.callPackage ./nix/pkgs/as/astal-py/package.nix {
              inherit gengir;
            };
            gengir = pkgs.callPackage ./nix/pkgs/ge/gengir/package.nix { };
            hyuga = pkgs.callPackage ./nix/pkgs/hy/hyuga/package.nix { };
          };

          formatter = pkgs.nixfmt-rfc-style;

          devShells = rec {
            default = cryosphere;
            cryosphere = pkgs.mkShell {
              buildInputs =
                with pkgs;
                with python313Packages;
                [
                  # Include all Astal libraries
                  inputs'.ags.packages.agsFull
                  self'.packages.astal-py
                  hy
                  self'.packages.hyuga
                  nil
                  nixfmt-rfc-style
                  python313
                ];
            };
          };
        };
    };
}
