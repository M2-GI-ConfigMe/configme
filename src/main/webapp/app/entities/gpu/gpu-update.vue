<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.gpu.home.createOrEditLabel"
          data-cy="GpuCreateUpdateHeading"
          v-text="$t('configmeApp.gpu.home.createOrEditLabel')"
        >
          Create or edit a Gpu
        </h2>
        <div>
          <div class="form-group" v-if="gpu.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="gpu.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.gpu.frequency')" for="gpu-frequency">Frequency</label>
            <input
              type="number"
              class="form-control"
              name="frequency"
              id="gpu-frequency"
              data-cy="frequency"
              :class="{ valid: !$v.gpu.frequency.$invalid, invalid: $v.gpu.frequency.$invalid }"
              v-model.number="$v.gpu.frequency.$model"
              required
            />
            <div v-if="$v.gpu.frequency.$anyDirty && $v.gpu.frequency.$invalid">
              <small class="form-text text-danger" v-if="!$v.gpu.frequency.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.gpu.frequency.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.gpu.memory')" for="gpu-memory">Memory</label>
            <input
              type="number"
              class="form-control"
              name="memory"
              id="gpu-memory"
              data-cy="memory"
              :class="{ valid: !$v.gpu.memory.$invalid, invalid: $v.gpu.memory.$invalid }"
              v-model.number="$v.gpu.memory.$model"
              required
            />
            <div v-if="$v.gpu.memory.$anyDirty && $v.gpu.memory.$invalid">
              <small class="form-text text-danger" v-if="!$v.gpu.memory.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.gpu.memory.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.gpu.consumption')" for="gpu-consumption">Consumption</label>
            <input
              type="number"
              class="form-control"
              name="consumption"
              id="gpu-consumption"
              data-cy="consumption"
              :class="{ valid: !$v.gpu.consumption.$invalid, invalid: $v.gpu.consumption.$invalid }"
              v-model.number="$v.gpu.consumption.$model"
              required
            />
            <div v-if="$v.gpu.consumption.$anyDirty && $v.gpu.consumption.$invalid">
              <small class="form-text text-danger" v-if="!$v.gpu.consumption.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.gpu.consumption.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.gpu.clockSpeed')" for="gpu-clockSpeed">Clock Speed</label>
            <input
              type="number"
              class="form-control"
              name="clockSpeed"
              id="gpu-clockSpeed"
              data-cy="clockSpeed"
              :class="{ valid: !$v.gpu.clockSpeed.$invalid, invalid: $v.gpu.clockSpeed.$invalid }"
              v-model.number="$v.gpu.clockSpeed.$model"
              required
            />
            <div v-if="$v.gpu.clockSpeed.$anyDirty && $v.gpu.clockSpeed.$invalid">
              <small class="form-text text-danger" v-if="!$v.gpu.clockSpeed.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.gpu.clockSpeed.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.gpu.lithography')" for="gpu-lithography">Lithography</label>
            <input
              type="number"
              class="form-control"
              name="lithography"
              id="gpu-lithography"
              data-cy="lithography"
              :class="{ valid: !$v.gpu.lithography.$invalid, invalid: $v.gpu.lithography.$invalid }"
              v-model.number="$v.gpu.lithography.$model"
              required
            />
            <div v-if="$v.gpu.lithography.$anyDirty && $v.gpu.lithography.$invalid">
              <small class="form-text text-danger" v-if="!$v.gpu.lithography.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.gpu.lithography.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.gpu.output')" for="gpu-output">Output</label>
            <input
              type="text"
              class="form-control"
              name="output"
              id="gpu-output"
              data-cy="output"
              :class="{ valid: !$v.gpu.output.$invalid, invalid: $v.gpu.output.$invalid }"
              v-model="$v.gpu.output.$model"
              required
            />
            <div v-if="$v.gpu.output.$anyDirty && $v.gpu.output.$invalid">
              <small class="form-text text-danger" v-if="!$v.gpu.output.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.gpu.inputPower')" for="gpu-inputPower">Input Power</label>
            <input
              type="text"
              class="form-control"
              name="inputPower"
              id="gpu-inputPower"
              data-cy="inputPower"
              :class="{ valid: !$v.gpu.inputPower.$invalid, invalid: $v.gpu.inputPower.$invalid }"
              v-model="$v.gpu.inputPower.$model"
              required
            />
            <div v-if="$v.gpu.inputPower.$anyDirty && $v.gpu.inputPower.$invalid">
              <small class="form-text text-danger" v-if="!$v.gpu.inputPower.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.gpu.bus')" for="gpu-bus">Bus</label>
            <select
              class="form-control"
              name="bus"
              :class="{ valid: !$v.gpu.bus.$invalid, invalid: $v.gpu.bus.$invalid }"
              v-model="$v.gpu.bus.$model"
              id="gpu-bus"
              data-cy="bus"
              required
            >
              <option value="PCI" v-bind:label="$t('configmeApp.BusType.PCI')">PCI</option>
              <option value="AGP" v-bind:label="$t('configmeApp.BusType.AGP')">AGP</option>
              <option value="PCI_EXPRESS_1" v-bind:label="$t('configmeApp.BusType.PCI_EXPRESS_1')">PCI_EXPRESS_1</option>
              <option value="PCI_EXPRESS_2" v-bind:label="$t('configmeApp.BusType.PCI_EXPRESS_2')">PCI_EXPRESS_2</option>
              <option value="PCI_EXPRESS_3" v-bind:label="$t('configmeApp.BusType.PCI_EXPRESS_3')">PCI_EXPRESS_3</option>
              <option value="PCI_EXPRESS_4" v-bind:label="$t('configmeApp.BusType.PCI_EXPRESS_4')">PCI_EXPRESS_4</option>
              <option value="PCI_EXPRESS_5" v-bind:label="$t('configmeApp.BusType.PCI_EXPRESS_5')">PCI_EXPRESS_5</option>
            </select>
            <div v-if="$v.gpu.bus.$anyDirty && $v.gpu.bus.$invalid">
              <small class="form-text text-danger" v-if="!$v.gpu.bus.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.gpu.dimension')" for="gpu-dimension">Dimension</label>
            <select class="form-control" id="gpu-dimension" data-cy="dimension" name="dimension" v-model="gpu.dimension">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="gpu.dimension && dimensionOption.id === gpu.dimension.id ? gpu.dimension : dimensionOption"
                v-for="dimensionOption in dimensions"
                :key="dimensionOption.id"
              >
                {{ dimensionOption.id }}
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
            :disabled="$v.gpu.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./gpu-update.component.ts"></script>
