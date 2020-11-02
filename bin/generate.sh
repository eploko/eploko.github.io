#!/bin/sh -ex
npm run build:css
lein generate
NODE_ENV=production npm run build:css
lein generate
git co -- docs/CNAME
