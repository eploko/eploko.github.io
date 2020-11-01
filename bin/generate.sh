#!/bin/sh -ex
npm run build:css
lein generate
git co -- docs/CNAME
