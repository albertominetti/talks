#!/usr/bin/env bash
TELEGRAM_TOKEN=''
curl -F "url=https://talks-minetti.herokuapp.com/telegram" 'https://api.telegram.org/bot${TELEGRAM_TOKEN}/setWebhook'