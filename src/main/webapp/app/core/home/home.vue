<template>
  <div class="height:100%">
    <v-layout fill-height class="grad-bg" style="height: calc(100vh - 64px)">
      <v-container>
        <v-row no-gutters>
          <v-col class="pt-10 text-white" style="margin-top: 10vh">
            <p class="text-h3">Bienvenue sur <span class="font-weight-bold">Config'Me</span></p>
            <p class="text-h5">Vous pouvez créer votre propre config' grâce à notre ConfigMaker !</p>
            <v-btn class="" @click="$vuetify.goTo('#configMaker', {})" large> C'est parti ! </v-btn>
          </v-col>
        </v-row>
      </v-container>
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
