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
        {
          packages = rec {
            default = cryosphere;
            cryosphere = pkgs.callPackage ./nix/pkgs/cr/cryosphere/package.nix {
              astalPackages = inputs'.astal.packages;
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
            cryosphere =
              let
                python = pkgs.python313.withPackages (
                  pythonPackages: with pythonPackages; [
                    self'.packages.astal-py
                    hy
                  ]
                );
              in
              pkgs.mkShell {
                buildInputs = with pkgs; [
                  self'.packages.hyuga
                  nil
                  nixfmt-rfc-style
                  python
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
