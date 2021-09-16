<template>
  <div class="height:100%">
    <v-layout fill-height class="grad-bg" style="height: calc(100vh - 64px)">
      <div class="col pt-10 pl-10 text-white" style="margin-top: 10vh; margin-left: 10vh">
        <p class="pl-10 h1">Bienvenue sur <span class="font-weight-bold">Config'Me</span></p>
        <p class="pl-10 h5">Vous pouvez créer votre propre config' grâce à notre ConfigMaker !</p>
        <v-btn class="ml-10" @click="$vuetify.goTo('#configMaker', {})"> C'est parti ! </v-btn>
      </div>
    </v-layout>

    <config-maker></config-maker>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>

<style>
.grad-bg {
  background: linear-gradient(229deg, #2196f3, #0068bb);
  background-size: 200% 200%;

  -webkit-animation: gradient 30s ease infinite;
  -moz-animation: gradient 30s ease infinite;
  animation: gradient 30s ease infinite;
}

@-webkit-keyframes gradient {
  0% {
    background-position: 0% 99%;
  }
  50% {
    background-position: 100% 2%;
  }
  100% {
    background-position: 0% 99%;
  }
}
@-moz-keyframes gradient {
  0% {
    background-position: 0% 99%;
  }
  50% {
    background-position: 100% 2%;
  }
  100% {
    background-position: 0% 99%;
  }
}
@keyframes gradient {
  0% {
    background-position: 0% 99%;
  }
  50% {
    background-position: 100% 2%;
  }
  100% {
    background-position: 0% 99%;
  }
}
</style>
