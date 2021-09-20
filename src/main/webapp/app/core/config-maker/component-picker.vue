<template>
  <div>
    <v-dialog v-model="show" fullscreen transition="slide-x-reverse-transition" content-class="component-picker" @click:outside.stop>
      <v-card class="d-flex flex-column">
        <v-toolbar dark color="primary" max-height="64" style="position: sticky; top: 0">
          <v-btn icon dark @click="activated = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>{{ this.displayName }}</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-items> </v-toolbar-items>
        </v-toolbar>
        <v-card-text class="flex-grow-1 d-flex flex-column px-0 pb-0">
          <v-data-table
            :headers="headers"
            :items="data.objects"
            :options.sync="options"
            :server-items-length="dataLength"
            :items-per-page="5"
            :loading="loading"
            loading-text="Chargement des données depuis le fin fond de l'univers..."
            class="elevation-0 flex-grow-1"
            @click:row="handleRowClick"
            hide-default-footer
            @page-count="pageCount = $event"
          >
            <template v-slot:item.stock="{ item }"><v-badge :color="item.stock > 0 ? 'success' : 'dark'"></v-badge></template>

            <template v-slot:item.hasVentirad="{ item }"
              ><v-icon :color="item.hasVentirad > 0 ? 'success' : 'red'">{{
                item.hasVentirad ? 'mdi-check' : 'mdi-close'
              }}</v-icon></template
            >
            <template v-slot:item.hasGpu="{ item }"
              ><v-icon :color="item.hasGpu > 0 ? 'success' : 'red'">{{ item.hasGpu ? 'mdi-check' : 'mdi-close' }}</v-icon></template
            >

            <template v-slot:item.hasThermalPaste="{ item }"
              ><v-icon :color="item.hasThermalPaste > 0 ? 'success' : 'red'">{{
                item.hasThermalPaste ? 'mdi-check' : 'mdi-close'
              }}</v-icon></template
            >

            <template v-slot:item.price="{ item }"> {{ item.price }}€ </template>
            <template v-slot:item.frequency="{ item }"> {{ item.frequency }}MHz </template>
            <template v-slot:item.memory="{ item }"> {{ item.memory }}Go </template>
            <template v-slot:item.capacity="{ item }"> {{ item.capacity }}Go </template>
            <template v-slot:item.power="{ item }"> {{ item.power }}W </template>
          </v-data-table>
          <div class="text-center pagination-sticky" style="background-color: #f9f9f9">
            <v-pagination v-model="page" :length="pageCount"></v-pagination>
          </div>
        </v-card-text>
      </v-card>
    </v-dialog>
    <v-overlay :value="show"> </v-overlay>
  </div>
</template>

<script lang="ts" src="./component-picker.component.ts"></script>

<style>
.pagination-sticky {
  position: sticky;
  bottom: 0;
}

.component-picker {
  width: 50%;
  right: 0;
  left: auto;
}

@media only screen and (min-device-width: 320px) and (max-device-width: 480px) {
  .component-picker {
    width: 100%;
  }
}
</style>
