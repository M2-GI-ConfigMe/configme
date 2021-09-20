<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.clientConfig.home.createOrEditLabel"
          data-cy="ClientConfigCreateUpdateHeading"
          v-text="$t('configmeApp.clientConfig.home.createOrEditLabel')"
        >
          Create or edit a ClientConfig
        </h2>
        <div>
          <div class="form-group" v-if="clientConfig.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="clientConfig.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.tags')" for="client-config-tags">Tags</label>
            <input
              type="text"
              class="form-control"
              name="tags"
              id="client-config-tags"
              data-cy="tags"
              :class="{ valid: !$v.clientConfig.tags.$invalid, invalid: $v.clientConfig.tags.$invalid }"
              v-model="$v.clientConfig.tags.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.name')" for="client-config-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="client-config-name"
              data-cy="name"
              :class="{ valid: !$v.clientConfig.name.$invalid, invalid: $v.clientConfig.name.$invalid }"
              v-model="$v.clientConfig.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.description')" for="client-config-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="client-config-description"
              data-cy="description"
              :class="{ valid: !$v.clientConfig.description.$invalid, invalid: $v.clientConfig.description.$invalid }"
              v-model="$v.clientConfig.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.isComlete')" for="client-config-isComlete"
              >Is Comlete</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="isComlete"
              id="client-config-isComlete"
              data-cy="isComlete"
              :class="{ valid: !$v.clientConfig.isComlete.$invalid, invalid: $v.clientConfig.isComlete.$invalid }"
              v-model="$v.clientConfig.isComlete.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.researchKey')" for="client-config-researchKey"
              >Research Key</label
            >
            <input
              type="text"
              class="form-control"
              name="researchKey"
              id="client-config-researchKey"
              data-cy="researchKey"
              :class="{ valid: !$v.clientConfig.researchKey.$invalid, invalid: $v.clientConfig.researchKey.$invalid }"
              v-model="$v.clientConfig.researchKey.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.cpuPrice')" for="client-config-cpuPrice"
              >Cpu Price</label
            >
            <input
              type="number"
              class="form-control"
              name="cpuPrice"
              id="client-config-cpuPrice"
              data-cy="cpuPrice"
              :class="{ valid: !$v.clientConfig.cpuPrice.$invalid, invalid: $v.clientConfig.cpuPrice.$invalid }"
              v-model.number="$v.clientConfig.cpuPrice.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.gpuPrice')" for="client-config-gpuPrice"
              >Gpu Price</label
            >
            <input
              type="number"
              class="form-control"
              name="gpuPrice"
              id="client-config-gpuPrice"
              data-cy="gpuPrice"
              :class="{ valid: !$v.clientConfig.gpuPrice.$invalid, invalid: $v.clientConfig.gpuPrice.$invalid }"
              v-model.number="$v.clientConfig.gpuPrice.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.ram1Price')" for="client-config-ram1Price"
              >Ram 1 Price</label
            >
            <input
              type="number"
              class="form-control"
              name="ram1Price"
              id="client-config-ram1Price"
              data-cy="ram1Price"
              :class="{ valid: !$v.clientConfig.ram1Price.$invalid, invalid: $v.clientConfig.ram1Price.$invalid }"
              v-model.number="$v.clientConfig.ram1Price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.ram2Price')" for="client-config-ram2Price"
              >Ram 2 Price</label
            >
            <input
              type="number"
              class="form-control"
              name="ram2Price"
              id="client-config-ram2Price"
              data-cy="ram2Price"
              :class="{ valid: !$v.clientConfig.ram2Price.$invalid, invalid: $v.clientConfig.ram2Price.$invalid }"
              v-model.number="$v.clientConfig.ram2Price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.psuPrice')" for="client-config-psuPrice"
              >Psu Price</label
            >
            <input
              type="number"
              class="form-control"
              name="psuPrice"
              id="client-config-psuPrice"
              data-cy="psuPrice"
              :class="{ valid: !$v.clientConfig.psuPrice.$invalid, invalid: $v.clientConfig.psuPrice.$invalid }"
              v-model.number="$v.clientConfig.psuPrice.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('configmeApp.clientConfig.computerCasePrice')"
              for="client-config-computerCasePrice"
              >Computer Case Price</label
            >
            <input
              type="number"
              class="form-control"
              name="computerCasePrice"
              id="client-config-computerCasePrice"
              data-cy="computerCasePrice"
              :class="{ valid: !$v.clientConfig.computerCasePrice.$invalid, invalid: $v.clientConfig.computerCasePrice.$invalid }"
              v-model.number="$v.clientConfig.computerCasePrice.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.ventiradPrice')" for="client-config-ventiradPrice"
              >Ventirad Price</label
            >
            <input
              type="number"
              class="form-control"
              name="ventiradPrice"
              id="client-config-ventiradPrice"
              data-cy="ventiradPrice"
              :class="{ valid: !$v.clientConfig.ventiradPrice.$invalid, invalid: $v.clientConfig.ventiradPrice.$invalid }"
              v-model.number="$v.clientConfig.ventiradPrice.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.hd1Price')" for="client-config-hd1Price"
              >Hd 1 Price</label
            >
            <input
              type="number"
              class="form-control"
              name="hd1Price"
              id="client-config-hd1Price"
              data-cy="hd1Price"
              :class="{ valid: !$v.clientConfig.hd1Price.$invalid, invalid: $v.clientConfig.hd1Price.$invalid }"
              v-model.number="$v.clientConfig.hd1Price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.hd2Price')" for="client-config-hd2Price"
              >Hd 2 Price</label
            >
            <input
              type="number"
              class="form-control"
              name="hd2Price"
              id="client-config-hd2Price"
              data-cy="hd2Price"
              :class="{ valid: !$v.clientConfig.hd2Price.$invalid, invalid: $v.clientConfig.hd2Price.$invalid }"
              v-model.number="$v.clientConfig.hd2Price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.cpu')" for="client-config-cpu">Cpu</label>
            <select class="form-control" id="client-config-cpu" data-cy="cpu" name="cpu" v-model="clientConfig.cpu">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="clientConfig.cpu && cpuOption.id === clientConfig.cpu.id ? clientConfig.cpu : cpuOption"
                v-for="cpuOption in cpus"
                :key="cpuOption.id"
              >
                {{ cpuOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.gpu')" for="client-config-gpu">Gpu</label>
            <select class="form-control" id="client-config-gpu" data-cy="gpu" name="gpu" v-model="clientConfig.gpu">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="clientConfig.gpu && gpuOption.id === clientConfig.gpu.id ? clientConfig.gpu : gpuOption"
                v-for="gpuOption in gpus"
                :key="gpuOption.id"
              >
                {{ gpuOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.psu')" for="client-config-psu">Psu</label>
            <select class="form-control" id="client-config-psu" data-cy="psu" name="psu" v-model="clientConfig.psu">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="clientConfig.psu && psuOption.id === clientConfig.psu.id ? clientConfig.psu : psuOption"
                v-for="psuOption in psus"
                :key="psuOption.id"
              >
                {{ psuOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.ventirad')" for="client-config-ventirad">Ventirad</label>
            <select class="form-control" id="client-config-ventirad" data-cy="ventirad" name="ventirad" v-model="clientConfig.ventirad">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  clientConfig.ventirad && ventiradOption.id === clientConfig.ventirad.id ? clientConfig.ventirad : ventiradOption
                "
                v-for="ventiradOption in ventirads"
                :key="ventiradOption.id"
              >
                {{ ventiradOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.mbe')" for="client-config-mbe">Mbe</label>
            <select class="form-control" id="client-config-mbe" data-cy="mbe" name="mbe" v-model="clientConfig.mbe">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="clientConfig.mbe && mbeOption.id === clientConfig.mbe.id ? clientConfig.mbe : mbeOption"
                v-for="mbeOption in mbes"
                :key="mbeOption.id"
              >
                {{ mbeOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.computerCase')" for="client-config-computerCase"
              >Computer Case</label
            >
            <select
              class="form-control"
              id="client-config-computerCase"
              data-cy="computerCase"
              name="computerCase"
              v-model="clientConfig.computerCase"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  clientConfig.computerCase && computerCaseOption.id === clientConfig.computerCase.id
                    ? clientConfig.computerCase
                    : computerCaseOption
                "
                v-for="computerCaseOption in computerCases"
                :key="computerCaseOption.id"
              >
                {{ computerCaseOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.hd1')" for="client-config-hd1">Hard Drive 1</label>
            <select class="form-control" id="client-config-hd1" data-cy="hd1" name="hd1" v-model="clientConfig.hd1">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="clientConfig.hd1 && hardDriveOption.id === clientConfig.hd1.id ? clientConfig.hd1 : hardDriveOption"
                v-for="hardDriveOption in hardDrives"
                :key="hardDriveOption.id"
              >
                {{ hardDriveOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.hd2')" for="client-config-hd2">Hard Drive 2</label>
            <select class="form-control" id="client-config-hd2" data-cy="hd2" name="hd2" v-model="clientConfig.hd2">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="clientConfig.hd2 && hardDriveOption.id === clientConfig.hd2.id ? clientConfig.hd2 : hardDriveOption"
                v-for="hardDriveOption in hardDrives"
                :key="hardDriveOption.id"
              >
                {{ hardDriveOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.ram1')" for="client-config-ram1">Ram 1</label>
            <select class="form-control" id="client-config-ram1" data-cy="ram1" name="ram1" v-model="clientConfig.ram1">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="clientConfig.ram1 && ramOption.id === clientConfig.ram1.id ? clientConfig.ram1 : ramOption"
                v-for="ramOption in rams"
                :key="ramOption.id"
              >
                {{ ramOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.clientConfig.ram2')" for="client-config-ram2">Ram 2</label>
            <select class="form-control" id="client-config-ram2" data-cy="ram2" name="ram2" v-model="clientConfig.ram2">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="clientConfig.ram2 && ramOption.id === clientConfig.ram2.id ? clientConfig.ram2 : ramOption"
                v-for="ramOption in rams"
                :key="ramOption.id"
              >
                {{ ramOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.clientConfig.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./client-config-update.component.ts"></script>
