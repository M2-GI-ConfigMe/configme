<template>
  <v-container style="height: calc(100vh - 64px)" class="pt-10">
    <v-row class="h-100">
      <v-col sm="12" lg="3">
        <v-card elevation="1">
          <v-navigation-drawer floating permanent width="100%">
            <template v-slot:prepend>
              <v-list-item two-line>
                <v-list-item-avatar>
                  <div class="rounded-circle primary text-no-wrap pa-7 text-white">{{ avatarName }}</div>
                </v-list-item-avatar>

                <v-list-item-content>
                  <v-list-item-title>{{ username }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </template>
            <v-divider class="my-2"></v-divider>
            <v-list list dense rounded>
              <v-list-item-group v-model="route" color="primary">
                <v-list-item v-for="route in routes" :key="route.title" link>
                  <v-list-item-icon>
                    <v-icon>{{ route.icon }}</v-icon>
                  </v-list-item-icon>

                  <v-list-item-content>
                    <v-list-item-title>{{ route.title }}</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-navigation-drawer>
        </v-card>
      </v-col>
      <v-col cols="6" class="pt-3 d-flex h-100">
        <transition name="slide-x-transition" mode="out-in">
          <router-view></router-view>
        </transition>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts" src="./home.component.ts"></script>
