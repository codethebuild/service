FROM npalm/java:oracle-java8
MAINTAINER Niek Palm "dev.npalm@gmail.com"

RUN  apt-get update
  && apt-get install -y \
     wget \
     curl \
     apt-transport-https \
     build-essential \
     git-core \
  && apt-get clean
  && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

RUN useradd -d /builder -u 1000 -m -s /bin/bash builder

ENV CONTAINER_INIT /usr/local/bin/init-container
RUN echo '#!/usr/bin/env bash' > $CONTAINER_INIT \
   && chmod +x $CONTAINER_INIT \
   && echo 'echo Container initialized.' >> $CONTAINER_INIT

USER builder
