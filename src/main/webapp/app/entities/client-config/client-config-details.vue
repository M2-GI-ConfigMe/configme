<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="clientConfig">
        <h2 class="jh-entity-heading" data-cy="clientConfigDetailsHeading">
          <span v-text="$t('configmeApp.clientConfig.detail.title')">ClientConfig</span> {{ clientConfig.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.clientConfig.tags')">Tags</span>
          </dt>
          <dd>
            <span>{{ clientConfig.tags }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.name')">Name</span>
          </dt>
          <dd>
            <span>{{ clientConfig.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.description')">Description</span>
          </dt>
          <dd>
            <span>{{ clientConfig.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.isComlete')">Is Comlete</span>
          </dt>
          <dd>
            <span>{{ clientConfig.isComlete }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.researchKey')">Research Key</span>
          </dt>
          <dd>
            <span>{{ clientConfig.researchKey }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.cpuPrice')">Cpu Price</span>
          </dt>
          <dd>
            <span>{{ clientConfig.cpuPrice }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.gpuPrice')">Gpu Price</span>
          </dt>
          <dd>
            <span>{{ clientConfig.gpuPrice }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.ram1Price')">Ram 1 Price</span>
          </dt>
          <dd>
            <span>{{ clientConfig.ram1Price }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.ram2Price')">Ram 2 Price</span>
          </dt>
          <dd>
            <span>{{ clientConfig.ram2Price }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.psuPrice')">Psu Price</span>
          </dt>
          <dd>
            <span>{{ clientConfig.psuPrice }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.computerCasePrice')">Computer Case Price</span>
          </dt>
          <dd>
            <span>{{ clientConfig.computerCasePrice }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.ventiradPrice')">Ventirad Price</span>
          </dt>
          <dd>
            <span>{{ clientConfig.ventiradPrice }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.hd1Price')">Hd 1 Price</span>
          </dt>
          <dd>
            <span>{{ clientConfig.hd1Price }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.hd2Price')">Hd 2 Price</span>
          </dt>
          <dd>
            <span>{{ clientConfig.hd2Price }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.cpu')">Cpu</span>
          </dt>
          <dd>
            <div v-if="clientConfig.cpu">
              <router-link :to="{ name: 'CpuView', params: { cpuId: clientConfig.cpu.id } }">{{ clientConfig.cpu.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.gpu')">Gpu</span>
          </dt>
          <dd>
            <div v-if="clientConfig.gpu">
              <router-link :to="{ name: 'GpuView', params: { gpuId: clientConfig.gpu.id } }">{{ clientConfig.gpu.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.psu')">Psu</span>
          </dt>
          <dd>
            <div v-if="clientConfig.psu">
              <router-link :to="{ name: 'PsuView', params: { psuId: clientConfig.psu.id } }">{{ clientConfig.psu.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.ventirad')">Ventirad</span>
          </dt>
          <dd>
            <div v-if="clientConfig.ventirad">
              <router-link :to="{ name: 'VentiradView', params: { ventiradId: clientConfig.ventirad.id } }">{{
                clientConfig.ventirad.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.mbe')">Mbe</span>
          </dt>
          <dd>
            <div v-if="clientConfig.mbe">
              <router-link :to="{ name: 'MbeView', params: { mbeId: clientConfig.mbe.id } }">{{ clientConfig.mbe.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.computerCase')">Computer Case</span>
          </dt>
          <dd>
            <div v-if="clientConfig.computerCase">
              <router-link :to="{ name: 'ComputerCaseView', params: { computerCaseId: clientConfig.computerCase.id } }">{{
                clientConfig.computerCase.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.hd1')">Hard Drive 1</span>
          </dt>
          <dd>
            <div v-if="clientConfig.hd1">
              <router-link :to="{ name: 'HardDriveView', params: { hardDriveId: clientConfig.hd1.id } }">{{
                clientConfig.hd1.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.hd2')">Hard Drive 2</span>
          </dt>
          <dd>
            <div v-if="clientConfig.hd2">
              <router-link :to="{ name: 'HardDriveView', params: { hardDriveId: clientConfig.hd2.id } }">{{
                clientConfig.hd2.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.ram1')">Ram 1</span>
          </dt>
          <dd>
            <div v-if="clientConfig.ram1">
              <router-link :to="{ name: 'RamView', params: { ramId: clientConfig.ram1.id } }">{{ clientConfig.ram1.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.clientConfig.ram2')">Ram 2</span>
          </dt>
          <dd>
            <div v-if="clientConfig.ram2">
              <router-link :to="{ name: 'RamView', params: { ramId: clientConfig.ram2.id } }">{{ clientConfig.ram2.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="clientConfig.id"
          :to="{ name: 'ClientConfigEdit', params: { clientConfigId: clientConfig.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./client-config-details.component.ts"></script>
