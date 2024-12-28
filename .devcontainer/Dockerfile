FROM ubuntu:22.04

# Install necessary packages
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
       wget \
       tar \
       sudo \
       maven \
       # Extras
       git \
       openssh-client \
       curl \
    && rm -rf /var/lib/apt/lists/*

# Create a non-root user (developer) with passwordless sudo
RUN useradd -m developer \
    && echo "developer ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers

# Install OpenJDK 21 (using Oracle's official tarball)
RUN mkdir -p /usr/lib/jvm \
    && cd /usr/lib/jvm \
    && wget -O /tmp/jdk.tar.gz https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz \
    && tar -xzf /tmp/jdk.tar.gz -C /usr/lib/jvm \
    && rm /tmp/jdk.tar.gz \
    # Link to "default-java" so tools can easily find it
    && ln -s /usr/lib/jvm/jdk-21 /usr/lib/jvm/default-java

# Set JAVA_HOME and update the PATH
ENV JAVA_HOME=/usr/lib/jvm/default-java
ENV PATH="$JAVA_HOME/bin:${PATH}"

# Switch to the non-root user
USER developer
WORKDIR /home/developer
