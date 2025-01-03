FROM ubuntu:22.04

# Install necessary packages
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
       software-properties-common \
       gnupg2

RUN add-apt-repository ppa:openjdk-r/ppa -y \
    && apt-get update \
    && apt-get install -y --no-install-recommends \
        sudo \
        maven \
        make \
        git \
        openssh-client \
        gnupg2 \
        curl \
        openjdk-21-jdk \
        python3 \
        python3-pip \
    && rm -rf /var/lib/apt/lists/*

# Optionally, set OpenJDK 21 as the default Java if multiple versions are installed.
# If it's the only JDK, it's typically already default. But just to be safe:
RUN update-alternatives --set java /usr/lib/jvm/java-21-openjdk-amd64/bin/java \
    && update-alternatives --set javac /usr/lib/jvm/java-21-openjdk-amd64/bin/javac

# Install Python dependencies
RUN ln -s /usr/bin/python3 /usr/bin/python \
    && pip install --upgrade pip \
    && pip install \
        pytest \
        JayDeBeApi

# Create a non-root user (developer) with passwordless sudo
RUN useradd -m developer \
    && echo "developer ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers

# Set JAVA_HOME as an environment variable
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:${PATH}"

# Switch to the non-root user
USER developer
WORKDIR /home/developer
