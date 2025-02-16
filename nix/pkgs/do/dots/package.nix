{
  buildNpmPackage,
  mk-deps-cache,
  babashka,
  clojure,
  src,
}:

let
  deps-cache = mk-deps-cache { lockfile = ./deps-lock.json; };
in

buildNpmPackage {
  pname = "dots";
  version = "unstable-0";

  inherit src;
  nativeBuildInputs = [
    babashka
    clojure
  ];

  npmDepsHash = "sha256-1P39RjnPocSef87YklvW90HDdxcp1g+lGjNS2bflxjU=";

  npmBuildScript = "release";

  # Make shadow-cljs use cached deps
  preBuild = ''
    bb -e '(-> (slurp "deps.edn")
               rewrite-clj.zip/of-string
               (rewrite-clj.zip/assoc :mvn/local-repo "${deps-cache}/.m2/repository")
               rewrite-clj.zip/root-string
               (#(spit "deps.edn" %)))'
  '';

  # Set name and version so `npm install` works
  # Set bin so `buildNpmPackage` installs wrapper
  preInstall = ''
    bb -e '(-> (slurp "package.json")
               json/parse-string
               (merge {:name "dots"
                       :version "0.0.1"
                       :bin {:dots "dist/dots.js"}})
               json/generate-string
               (#(spit "package.json" %)))'
  '';
}
