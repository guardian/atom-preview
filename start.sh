#!/usr/bin/env bash

set -e

sbt run & yarn --cwd frontend start
