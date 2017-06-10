#!/usr/bin/env bash

if [[ -z $TELEGRAM_TOKEN ]]; then
  echo "You must specify the TELEGRAM_TOKEN variable"
  exit 1;
fi
curl -F "url=https://talks-minetti.herokuapp.com/telegram" 'https://api.telegram.org/bot${TELEGRAM_TOKEN}/setWebhook'