<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="mbe">
        <h2 class="jh-entity-heading" data-cy="mbeDetailsHeading">
          <span v-text="$t('configmeApp.mbe.detail.title')">Mbe</span> {{ mbe.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.mbe.socketCpu')">Socket Cpu</span>
          </dt>
          <dd>
            <span v-text="$t('configmeApp.SocketType.' + mbe.socketCpu)">{{ mbe.socketCpu }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.mbe.ramType')">Ram Type</span>
          </dt>
          <dd>
            <span v-text="$t('configmeApp.RamType.' + mbe.ramType)">{{ mbe.ramType }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.mbe.ramFrequencyMax')">Ram Frequency Max</span>
          </dt>
          <dd>
            <span>{{ mbe.ramFrequencyMax }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.mbe.ramSizeMax')">Ram Size Max</span>
          </dt>
          <dd>
            <span>{{ mbe.ramSizeMax }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.mbe.pciOutputs')">Pci Outputs</span>
          </dt>
          <dd>
            <span>{{ mbe.pciOutputs }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.mbe.displayOutput')">Display Output</span>
          </dt>
          <dd>
            <span>{{ mbe.displayOutput }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.mbe.storageOutput')">Storage Output</span>
          </dt>
          <dd>
            <span>{{ mbe.storageOutput }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.mbe.insideIO')">Inside IO</span>
          </dt>
          <dd>
            <span>{{ mbe.insideIO }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.mbe.backPanelOutput')">Back Panel Output</span>
          </dt>
          <dd>
            <span>{{ mbe.backPanelOutput }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.mbe.bios')">Bios</span>
          </dt>
          <dd>
            <span>{{ mbe.bios }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.mbe.format')">Format</span>
          </dt>
          <dd>
            <span v-text="$t('configmeApp.FormatType.' + mbe.format)">{{ mbe.format }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="mbe.id" :to="{ name: 'MbeEdit', params: { mbeId: mbe.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./mbe-details.component.ts"></script>
