<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.cpu.home.createOrEditLabel"
          data-cy="CpuCreateUpdateHeading"
          v-text="$t('configmeApp.cpu.home.createOrEditLabel')"
        >
          Create or edit a Cpu
        </h2>
        <div>
          <div class="form-group" v-if="cpu.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="cpu.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.cpu.frequency')" for="cpu-frequency">Frequency</label>
            <input
              type="number"
              class="form-control"
              name="frequency"
              id="cpu-frequency"
              data-cy="frequency"
              :class="{ valid: !$v.cpu.frequency.$invalid, invalid: $v.cpu.frequency.$invalid }"
              v-model.number="$v.cpu.frequency.$model"
              required
            />
            <div v-if="$v.cpu.frequency.$anyDirty && $v.cpu.frequency.$invalid">
              <small class="form-text text-danger" v-if="!$v.cpu.frequency.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.cpu.frequency.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.cpu.cacheL1')" for="cpu-cacheL1">Cache L 1</label>
            <input
              type="number"
              class="form-control"
              name="cacheL1"
              id="cpu-cacheL1"
              data-cy="cacheL1"
              :class="{ valid: !$v.cpu.cacheL1.$invalid, invalid: $v.cpu.cacheL1.$invalid }"
              v-model.number="$v.cpu.cacheL1.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.cpu.cacheL2')" for="cpu-cacheL2">Cache L 2</label>
            <input
              type="number"
              class="form-control"
              name="cacheL2"
              id="cpu-cacheL2"
              data-cy="cacheL2"
              :class="{ valid: !$v.cpu.cacheL2.$invalid, invalid: $v.cpu.cacheL2.$invalid }"
              v-model.number="$v.cpu.cacheL2.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.cpu.cacheL3')" for="cpu-cacheL3">Cache L 3</label>
            <input
              type="number"
              class="form-control"
              name="cacheL3"
              id="cpu-cacheL3"
              data-cy="cacheL3"
              :class="{ valid: !$v.cpu.cacheL3.$invalid, invalid: $v.cpu.cacheL3.$invalid }"
              v-model.number="$v.cpu.cacheL3.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.cpu.nbHeart')" for="cpu-nbHeart">Nb Heart</label>
            <input
              type="number"
              class="form-control"
              name="nbHeart"
              id="cpu-nbHeart"
              data-cy="nbHeart"
              :class="{ valid: !$v.cpu.nbHeart.$invalid, invalid: $v.cpu.nbHeart.$invalid }"
              v-model.number="$v.cpu.nbHeart.$model"
              required
            />
            <div v-if="$v.cpu.nbHeart.$anyDirty && $v.cpu.nbHeart.$invalid">
              <small class="form-text text-danger" v-if="!$v.cpu.nbHeart.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.cpu.nbHeart.min" v-text="$t('entity.validation.min', { min: 1 })">
                This field should be at least 1.
              </small>
              <small class="form-text text-danger" v-if="!$v.cpu.nbHeart.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.cpu.nbThread')" for="cpu-nbThread">Nb Thread</label>
            <input
              type="number"
              class="form-control"
              name="nbThread"
              id="cpu-nbThread"
              data-cy="nbThread"
              :class="{ valid: !$v.cpu.nbThread.$invalid, invalid: $v.cpu.nbThread.$invalid }"
              v-model.number="$v.cpu.nbThread.$model"
              required
            />
            <div v-if="$v.cpu.nbThread.$anyDirty && $v.cpu.nbThread.$invalid">
              <small class="form-text text-danger" v-if="!$v.cpu.nbThread.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.cpu.nbThread.min" v-text="$t('entity.validation.min', { min: 1 })">
                This field should be at least 1.
              </small>
              <small class="form-text text-danger" v-if="!$v.cpu.nbThread.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.cpu.hasVentirad')" for="cpu-hasVentirad">Has Ventirad</label>
            <input
              type="checkbox"
              class="form-check"
              name="hasVentirad"
              id="cpu-hasVentirad"
              data-cy="hasVentirad"
              :class="{ valid: !$v.cpu.hasVentirad.$invalid, invalid: $v.cpu.hasVentirad.$invalid }"
              v-model="$v.cpu.hasVentirad.$model"
              required
            />
            <div v-if="$v.cpu.hasVentirad.$anyDirty && $v.cpu.hasVentirad.$invalid">
              <small class="form-text text-danger" v-if="!$v.cpu.hasVentirad.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.cpu.socketType')" for="cpu-socketType">Socket Type</label>
            <select
              class="form-control"
              name="socketType"
              :class="{ valid: !$v.cpu.socketType.$invalid, invalid: $v.cpu.socketType.$invalid }"
              v-model="$v.cpu.socketType.$model"
              id="cpu-socketType"
              data-cy="socketType"
              required
            >
              <option value="AM4" v-bind:label="$t('configmeApp.SocketType.AM4')">AM4</option>
              <option value="TR4" v-bind:label="$t('configmeApp.SocketType.TR4')">TR4</option>
              <option value="LGA_1150" v-bind:label="$t('configmeApp.SocketType.LGA_1150')">LGA_1150</option>
              <option value="LGA_1151" v-bind:label="$t('configmeApp.SocketType.LGA_1151')">LGA_1151</option>
              <option value="LGA_2066" v-bind:label="$t('configmeApp.SocketType.LGA_2066')">LGA_2066</option>
              <option value="LGA_1200" v-bind:label="$t('configmeApp.SocketType.LGA_1200')">LGA_1200</option>
            </select>
            <div v-if="$v.cpu.socketType.$anyDirty && $v.cpu.socketType.$invalid">
              <small class="form-text text-danger" v-if="!$v.cpu.socketType.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.cpu.lithography')" for="cpu-lithography">Lithography</label>
            <input
              type="number"
              class="form-control"
              name="lithography"
              id="cpu-lithography"
              data-cy="lithography"
              :class="{ valid: !$v.cpu.lithography.$invalid, invalid: $v.cpu.lithography.$invalid }"
              v-model.number="$v.cpu.lithography.$model"
              required
            />
            <div v-if="$v.cpu.lithography.$anyDirty && $v.cpu.lithography.$invalid">
              <small class="form-text text-danger" v-if="!$v.cpu.lithography.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.cpu.lithography.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.cpu.ramFrequencyMax')" for="cpu-ramFrequencyMax"
              >Ram Frequency Max</label
            >
            <input
              type="number"
              class="form-control"
              name="ramFrequencyMax"
              id="cpu-ramFrequencyMax"
              data-cy="ramFrequencyMax"
              :class="{ valid: !$v.cpu.ramFrequencyMax.$invalid, invalid: $v.cpu.ramFrequencyMax.$invalid }"
              v-model.number="$v.cpu.ramFrequencyMax.$model"
              required
            />
            <div v-if="$v.cpu.ramFrequencyMax.$anyDirty && $v.cpu.ramFrequencyMax.$invalid">
              <small class="form-text text-danger" v-if="!$v.cpu.ramFrequencyMax.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.cpu.ramFrequencyMax.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.cpu.consumption')" for="cpu-consumption">Consumption</label>
            <input
              type="number"
              class="form-control"
              name="consumption"
              id="cpu-consumption"
              data-cy="consumption"
              :class="{ valid: !$v.cpu.consumption.$invalid, invalid: $v.cpu.consumption.$invalid }"
              v-model.number="$v.cpu.consumption.$model"
              required
            />
            <div v-if="$v.cpu.consumption.$anyDirty && $v.cpu.consumption.$invalid">
              <small class="form-text text-danger" v-if="!$v.cpu.consumption.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.cpu.consumption.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.cpu.hasGpu')" for="cpu-hasGpu">Has Gpu</label>
            <input
              type="checkbox"
              class="form-check"
              name="hasGpu"
              id="cpu-hasGpu"
              data-cy="hasGpu"
              :class="{ valid: !$v.cpu.hasGpu.$invalid, invalid: $v.cpu.hasGpu.$invalid }"
              v-model="$v.cpu.hasGpu.$model"
              required
            />
            <div v-if="$v.cpu.hasGpu.$anyDirty && $v.cpu.hasGpu.$invalid">
              <small class="form-text text-danger" v-if="!$v.cpu.hasGpu.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
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
            :disabled="$v.cpu.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./cpu-update.component.ts"></script>
