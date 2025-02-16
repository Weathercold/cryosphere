{ buildNpmPackage }:
buildNpmPackage {
  pname = "cryosphere";
  version = "unstable-0.0.1";

  src = ../../../../.;

  npmDepsHash = "sha256-bgImGWrW8kJF6ijU+3Hif3LNpzn5nrRVCOo32xK0bp4=";
}
