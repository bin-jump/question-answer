#!/bin/sh

set -e
  
host="$1"
shift
cmd="$@"
  
until  wget "http://${host}:9200/_cat/health" -O '/dev/null'; do
  >&2 echo "Elasticsearch is unavailable - sleeping"
  sleep 1
done
  
>&2 echo "Elasticsearch is up - continue"
exec $cmd